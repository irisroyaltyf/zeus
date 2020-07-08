package top.yulegou.zeus.manager;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.crawler.ContentCrawler;
import top.yulegou.zeus.crawler.SourceUrlCrawler;
import top.yulegou.zeus.dao.domain.*;
import top.yulegou.zeus.dao.domain.flux.ZFuxEmitter;
import top.yulegou.zeus.dao.domain.publish.ZBasePublishRuleConfig;
import top.yulegou.zeus.dao.mapper.ZTaskMapper;
import top.yulegou.zeus.domain.ContentCollectedDTO;
import top.yulegou.zeus.domain.PublishResult;
import top.yulegou.zeus.publish.BasePublishExecutor;
import top.yulegou.zeus.publish.PublishCreator;
import top.yulegou.zeus.task.quartz.QuartzManager;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ZeusTaskManager {

    @Autowired
    private ZTaskMapper zTaskMapper;
    @Autowired
    private ZeusCrawlerRuleManager zeusCrawlerRuleManager;
    @Autowired
    private ContentCrawler contentCrawler;
    @Autowired
    private SourceUrlCrawler sourceUrlCrawler;
    @Autowired
    private ZeusPublishRuleManager publishRuleManager;
    @Autowired
    private QuartzManager quartzManager;


    public Flux<String> collectForController(final Integer taskId) throws Exception {
        ZTask task =  getTaskById(taskId);
        if (task == null) {
            throw new Exception("任务获取错误");
        }
        ZCrawlerRule zCrawlerRule = zeusCrawlerRuleManager.getCrawlerRuleByTaskId(taskId);
        if (zCrawlerRule == null) {
            throw new Exception("抓取规则没有配置");
        }
        ZCrawlerRuleConfig ruleConfig = zCrawlerRule.getCrawlerRuleConfig();
        if (ruleConfig == null) {
            throw new Exception("抓取规则没有配置");
        }
        List<String> urls = ruleConfig.getFromUrls();
        // TODO 可以并发下载
        if (urls == null || urls.isEmpty()) {
            throw new Exception("起始页为空");
        }

        return Flux.just(urls.toArray(new String[urls.size()]))
                .delayElements(Duration.ofSeconds(0)) // delay 会让map 或者flatmap在default scheduler 执行
                .flatMap(url->{
            List<String> contentUrls = null;
                    int urlValid = 0;
                    int urlInvalid = 0;
            if (ruleConfig.getSourceIsContent() == null || ruleConfig.getSourceIsContent() == false) {
                // crawler page url
                // TODO  对采集到的网址进行判断 是否有效
                contentUrls = sourceUrlCrawler.getUrls(url, task.getzTaskConfig(), ruleConfig.getZCrawlerUrlConfig());
                urlValid = contentUrls.size();
                urlInvalid = 0;
            } else {
                contentUrls = new ArrayList<>();
                contentUrls.add(url);
            }
            final AtomicReference<ZFuxEmitter> emitter = new AtomicReference<>();
            AtomicInteger count = new AtomicInteger(0);
            Flux<String> publishRst = Flux.create(s ->{
                emitter.set(new ZFuxEmitter(s, task, publishRuleManager));
            });
            Flux<String> rst = Flux
                .just(contentUrls.toArray(new String[contentUrls.size()]))
                    .delayElements(Duration.ofSeconds(0))
                    .flatMap(contentUrl -> {
                        JSONObject obj = new JSONObject();
                        // crawer content
                        ContentCollectedDTO contentCollectedDTO =
                                contentCrawler.collectField(contentUrl, task, ruleConfig.getZCrawlerContentConfig());
                        if (contentCollectedDTO != null && contentCollectedDTO.isSuccess()) {
                            //此处多scheduler有问题
                            emitter.get().emit(contentCollectedDTO);
                        } else {
                            obj.put("contentCrawlerSuccess", false);
                        }
                        obj.put("type", "url");
                        obj.put("contentUrl", contentUrl);
                        if (!StringUtils.equals(contentUrl, url)) {
                            obj.put("fromUrl", url);
                        }
                        return  Flux.just(obj.toJSONString());
                    })
//                    .filter(v -> StringUtils.isNotEmpty(v))
                    .take(10)
                    .doOnComplete(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("complete");
                            emitter.get().complete();
                        }
                    });

            if (ruleConfig.getSourceIsContent() == null || ruleConfig.getSourceIsContent() == false) {
                JSONObject object = new JSONObject();
                object.put("type", "fromUrl");
                object.put("url", url);
                object.put("validUrlCnt", urlValid);
                object.put("invalidUrl", urlInvalid);
                object.put("contentUrlCount", contentUrls == null ? 0 : contentUrls.size());
                return Flux.merge(Flux.just(object.toJSONString()), rst, publishRst);
            } else {
                return Flux.merge(rst, publishRst) ;
            }
        }).doOnComplete(new Runnable() {
                    @Override
                    public void run() {
                        task.setLastCaiji(new Date().getTime());
                        update(task);
                    }
                });
    }
    public void collect(ZTask task) {
        ZCrawlerRule zCrawlerRule = zeusCrawlerRuleManager.getCrawlerRuleByTaskId(task.getId());
        if (zCrawlerRule == null) {
            log.info("任务 " + task.gettName() + " 抓取规则没有配置");
            return ;
        }
        ZCrawlerRuleConfig ruleConfig = zCrawlerRule.getCrawlerRuleConfig();
        if (ruleConfig == null) {
            log.info("任务 " + task.gettName() + " 抓取规则没有配置");
            return ;
        }
        ZPublishRule publishRule = publishRuleManager.getPublishRuleByTaskId(task.getId());
        if (publishRule == null || publishRule.getRuleConfig() == null) {
            log.info("任务 " + task.gettName() + " 发布规则没有配置");
            return ;
        }
        List<String> urls = ruleConfig.getFromUrls();
        // TODO 可以并发下载
        if (urls == null || urls.isEmpty()) {
            log.info("任务 " + task.gettName() + " 起始页为空");
        }
        // 1. content url
        final List<String> contentUrls = new ArrayList<>();
        urls.stream().forEach(url -> {
            if (ruleConfig.getSourceIsContent() == null || ruleConfig.getSourceIsContent() == false) {
                // crawler page url
                // TODO  对采集到的网址进行判断 是否有效
                contentUrls.addAll(sourceUrlCrawler.getUrls(url, task.getzTaskConfig(), ruleConfig.getZCrawlerUrlConfig()));
            } else {
                contentUrls.add(url);
            }
        });
        // 2. collect content
        List<ContentCollectedDTO> collectedDTOS = contentUrls.stream().map((contentUrl -> {
            //TODO 可能需要延迟
            ContentCollectedDTO contentCollectedDTO =
                    contentCrawler.collectField(contentUrl, task, ruleConfig.getZCrawlerContentConfig());
            return contentCollectedDTO;
        })).sequential().collect(Collectors.toList());
        collectedDTOS.stream().forEach(contentCollectedDTO -> {
            //DO publish
            ZBasePublishRuleConfig publishRuleConfig = publishRule.getRuleConfig();
            BasePublishExecutor executor = PublishCreator.create(publishRuleConfig);
            PublishResult publishResult = executor.publish(contentCollectedDTO, task, publishRule);
            if(publishResult.isSuccess()) {
                // 记录数据库 成功
                log.info("成功 发布" + contentCollectedDTO.getUrl());
            } else {
                log.info("失败 " + contentCollectedDTO.getUrl());
            }
        });
    }
    public void collect(Integer taskId) {
        ZTask task = getTaskById(taskId);
        if (task == null) {
            log.error("collect with wrong taskId " + task);
        } else if (task.getStatus() == 1) {
            log.info("task " + task.gettName() + " 正在运行中, 本次采集放弃.");
        } else {
            collect(task);
        }
    }
    public int insert(ZTask task) {
        try {
            Date now = new Date();
            Long time = now.getTime();
            task.setGmtCreate(time);
            task.setGmtModified(time);
            return zTaskMapper.insertSelective(task);
        }catch (Exception e) {
            log.error("ztask insert error " + JSONObject.toJSONString(task), e);
        }
        return 0;
    }
    public int update(ZTask task) {
        try {
            return zTaskMapper.updateByPrimaryKeySelective(task);
        } catch (Exception e ) {
            log.error("ztask update error " + JSONObject.toJSONString(task), e);
        }
        return 0;
    }

    public ZTask getTaskById(Integer taskId) {
        return zTaskMapper.selectByPrimaryKey(taskId);
    }

    public List<ZTask> selectTask() {
        ZTaskExample example = new ZTaskExample();
//        ZTaskExample.Criteria criteria = example.createCriteria();
        return zTaskMapper.selectByExampleWithBLOBs(example);
    }

    public long countTask(String taskName, int groupId) {
        ZTaskExample example = new ZTaskExample();
        ZTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTNameEqualTo(taskName).andGroupIdEqualTo(groupId);
        return zTaskMapper.countByExample(example);
    }

    public int deleteTaskById(Integer taskId) {
        return zTaskMapper.deleteByPrimaryKey(taskId);
    }

    public void operateTask(Integer taskId) {
        ZTask task = getTaskById(taskId);
        if (task == null) {
            log.error("start or stop task error");
        }
        operateTask(task);
    }

    public void operateTask(ZTask task) {
        try {
            if (task.gettAuto() != null && task.gettAuto() == 1
                    && StringUtils.isNotEmpty(task.getCron())) {
                quartzManager.operateJob(task, Constants.OPERATE_JOB_START);
                log.info("任务 " + task.gettName() + " 启动定时任务,定时规则 " + task.getCron());
            } else {
                quartzManager.operateJob(task, Constants.OPERATE_JOB_STOP);
                log.info("任务 " + task.gettName() + " 定时任务关闭");

            }
        } catch (Exception e) {
            log.error("operate job error", e);
        }
    }

    @PostConstruct
    public void startTasks() {
        List<ZTask> tasks = selectTask();
        tasks.forEach(task -> {
            if (task.gettAuto() != null && task.gettAuto() == 1
                    && StringUtils.isNotEmpty(task.getCron())) {
                operateTask(task);
            }
        });
    }
}
