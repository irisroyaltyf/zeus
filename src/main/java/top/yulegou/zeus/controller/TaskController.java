package top.yulegou.zeus.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import top.yulegou.zeus.config.template.RandomId;
import top.yulegou.zeus.config.template.UUIDTemplateMethod;
import top.yulegou.zeus.constant.ErrorCode;
import top.yulegou.zeus.dao.domain.*;
import top.yulegou.zeus.domain.*;
import top.yulegou.zeus.manager.ZeusCrawlerRuleManager;
import top.yulegou.zeus.manager.ZeusPublishRuleManager;
import top.yulegou.zeus.manager.ZeusTaskManager;
import top.yulegou.zeus.task.quartz.QuartzManager;

import java.text.ParseException;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/task")
public class TaskController extends BaseController {
    @Autowired
    private ZeusTaskManager zeusTaskManager;
    @Autowired
    private ZeusCrawlerRuleManager zeusCrawlerRuleManager;

    @RequestMapping("/list")
    public String taskList(final Model model) {
        List<ZTask> tasks = zeusTaskManager.selectTask();
        tasks.stream().forEach(task -> {
            if (task.gettAuto() != null &&  task.gettAuto() == 1) {
                task.setNextTime(QuartzManager.getNextTriggerTime(task.getCron()));
            }
        });
        model.addAttribute("tasks", tasks);
        return "task/list";
    }
    @RequestMapping("/add")
    public String taskAdd(){
        return "task/edit";
    }

    @RequestMapping("/edit")
    public String taskEdit(final Model model,
                           @RequestParam(value = "task_id", required = true, defaultValue = "0")Integer taskId) {
        if (taskId == 0) {
            return errorPage(model, "缺少相关任务id");
        }
        ZTask task = zeusTaskManager.getTaskById(taskId);
        if (task == null) {
            return  errorPage(model, "任务不存在");
        }
        model.addAttribute("task", task);
        return "task/edit";
    }
    @RequestMapping("/list.data")
    @ResponseBody
    public String getTaskList(
            @RequestParam(value = "page", defaultValue = "1") Integer start,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder
    ) {
        JSONObject object = new JSONObject();
        int off = (start - 1) * size;
        off = Math.max(0, off);
        ZTaskExample example = new ZTaskExample();
        example.setLimit(size);
        example.setOffset(off);
        example.setOrderByClause(" id " + sortOrder);
        List<ZTask> tasks = zeusTaskManager.selectTask(example);
        tasks.stream().forEach(task -> {
            if (task.gettAuto() != null &&  task.gettAuto() == 1) {
                task.setNextTime(QuartzManager.getNextTriggerTime(task.getCron()));
            }
        });
        object.put("rows", tasks);
        object.put("total", zeusTaskManager.countTasks());
        return object.toJSONString();
    }

    @RequestMapping("/auto.do")
    @ResponseBody
    public String taskAuto(
            TaskDTO taskDTO
    ) {
        if (taskDTO.getTaskId() == null || taskDTO.getAuto() == null) {
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "参数错误");
        }
        ZTask task = new ZTask();
        task.setId(taskDTO.getTaskId());
        task.settAuto(taskDTO.getAuto());
        task.setCron(taskDTO.getCronTime());
        task.setGmtModified(System.currentTimeMillis());
        if (task.gettAuto() != null && task.gettAuto() == 1
                && StringUtils.isEmpty(task.getCron())) {
            ZTask task1 = zeusTaskManager.getTaskById(task.getId());
            if (StringUtils.isEmpty(task1.getCron())) {
                return Result.failed(ErrorCode.OTHER_ERROR_CRON_EMPTY.getCode(), "需要先配置任务执行时间");
            }
        }
        int rst = zeusTaskManager.update(task);
        if(rst > 0) {
            zeusTaskManager.operateTask(task.getId());
            return Result.success();
        } else {
            return Result.failed(ErrorCode.DB_ERROR_FAILED.getCode(), "数据更新错误");
        }
    }

    /**
     * 采集规则页面
     * @param model
     * @param taskId
     * @return
     */
    @RequestMapping("/crawler")
    public String crawlerEdit(
            final Model model,
            @RequestParam(value = "task_id", required = true, defaultValue = "0")Integer taskId
    ) {
        if (taskId == 0) {
            return errorPage(model, "缺少相关任务id");
        }
        ZCrawlerRule crawlerRule = zeusCrawlerRuleManager.getCrawlerRuleByTaskId(taskId);
        model.addAttribute("crawlerRule", crawlerRule);
        if (crawlerRule != null) {
            model.addAttribute("crawlerRuleConfig", crawlerRule.getCrawlerRuleConfig());
            if (crawlerRule.getCrawlerRuleConfig() != null) {
                model.addAttribute("crawlerUrlConfig", crawlerRule.getCrawlerRuleConfig().getZCrawlerUrlConfig());
                ZCrawlerContentConfig contentConfig = crawlerRule.getCrawlerRuleConfig().getZCrawlerContentConfig();
                if (contentConfig != null && contentConfig.getFieldConfigList() != null) {
                    List<ZCrawlerFieldConfig> fieldList = contentConfig.getFieldConfigList().stream().map(fieldConfig->{
                        String rst = JSONObject.toJSONString(fieldConfig);
                        Base64.Encoder encoder = Base64.getEncoder();
                        String rstData = new String(encoder.encode(rst.getBytes()));
                        fieldConfig.setBase64Config(rstData);
                        return fieldConfig;
                    }).collect(Collectors.toList());
                    model.addAttribute("fieldList", fieldList);
                }
            }
        }
        model.addAttribute("randomId", new RandomId());
        model.addAttribute("uuid", new UUIDTemplateMethod());
        model.addAttribute("taskId", taskId);
        return "/task/crawler";
    }

    @RequestMapping("/crawler/savecrawler.do")
    @ResponseBody
    public String crawerAddOrUpdate(
            @RequestParam(value = "crawler_id", required = false, defaultValue = "0") Integer crawlerId,
            @RequestParam(value = "task_name", required = true) String taskName,
            @RequestParam(value = "auto", required = true, defaultValue = "0") int auto
    ) {
        if (crawlerId == 0) {
            // add
        } else {
            // update
        }
        return Result.success();
    }

    /**
     * 保存或者更新起始页网址配置
     * @param beginUrlDTO
     * @return
     */
    @RequestMapping("/crawler/savebeginurl.do")
    @ResponseBody
    public String saveFromUrl(
            BeginUrlDTO beginUrlDTO
    ) {
        ZCrawlerRule crawlerRule = null;
        try {
            crawlerRule = checkAndGetRule(beginUrlDTO.getTaskId(), beginUrlDTO.getCrawlerId());
        } catch (Exception e) {
            log.error("get crawler rule error", e);
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "规则id不正确,请刷新后重试");
        }
        ZCrawlerRuleConfig ruleConfig = crawlerRule.getCrawlerRuleConfig();
        if (ruleConfig == null) {
            ruleConfig = new ZCrawlerRuleConfig();
        }
        if (beginUrlDTO.getFromUrls() != null) {
            ruleConfig.setFromUrls(beginUrlDTO.getFromUrls().stream().filter(url -> StringUtils.isNotEmpty(url)).collect(Collectors.toList()));
        }
        ruleConfig.setSourceIsContent(beginUrlDTO.getSourceIsContent());
        crawlerRule.setzCrawlerRuleConfig(ruleConfig);
        int rst = zeusCrawlerRuleManager.addOrUpdateZrawlerRule(crawlerRule);
        if (rst > 0) {
            return Result.success(crawlerRule.getId());
        }
        return Result.failed(ErrorCode.SYSTEM_ERROR.getCode(), "更新错误,请刷新后重试");
    }


    /**
     * 采集内容url配置
     * @param crawlerUrlDTO
     * @return
     */
    @RequestMapping("/crawler/savecontenturl.do")
    @ResponseBody
    public String saveOrUpdateCotentUrlConfig(
            CrawlerUrlDTO crawlerUrlDTO
            ) {
        ZCrawlerRule rule = null;
        try {
            rule = checkAndGetRule(crawlerUrlDTO.getTaskId(), crawlerUrlDTO.getCrawlerId());
        } catch (Exception e) {
            log.error("get crawler rule error", e);
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "规则id不正确,请刷新后重试");
        }
        ZCrawlerRuleConfig ruleConfig = rule.getCrawlerRuleConfig();
        ZCrawlerUrlConfig urlConfig = null;
        if (ruleConfig == null) {
            ruleConfig = new ZCrawlerRuleConfig();
        } else {
            urlConfig = ruleConfig.getZCrawlerUrlConfig();
        }
        if (urlConfig == null) {
            urlConfig = new ZCrawlerUrlConfig();
            ruleConfig.setZCrawlerUrlConfig(urlConfig);
        }
        urlConfig.setContentArea(crawlerUrlDTO.getUrlArea());
        urlConfig.setContentUrlRule(crawlerUrlDTO.getContentUrlRule());
        urlConfig.setFinalMergeRule(crawlerUrlDTO.getFinalMergeUrl());
        rule.setzCrawlerRuleConfig(ruleConfig);
        int rst = zeusCrawlerRuleManager.addOrUpdateZrawlerRule(rule);
        if (rst > 0) {
            return Result.success(rule.getId());
        }
        return Result.failed(ErrorCode.SYSTEM_ERROR.getCode(), "更新错误,请刷新后重试");
    }

    /**
     * 保存抓取内容页面配置
     * @param crawlerContentDTO
     * @return
     */
    @RequestMapping("/crawler/savecrawlercontent.do")
    @ResponseBody
    public String saveCrawlerContent(
            CrawlerContentDTO crawlerContentDTO
    ) {
        ZCrawlerRule crawlerRule = null;
        try {
            crawlerRule = checkAndGetRule(crawlerContentDTO.getTaskId(), crawlerContentDTO.getCrawlerId());
        } catch (Exception e) {
            log.error("get crawler rule error", e);
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "规则id不正确,请刷新后重试");
        }
        ZCrawlerRuleConfig ruleConfig = crawlerRule.getCrawlerRuleConfig();
        ZCrawlerContentConfig contentConfig = null;
        if (ruleConfig == null) {
            ruleConfig = new ZCrawlerRuleConfig();
        } else {
            contentConfig = ruleConfig.getZCrawlerContentConfig();
        }
        if (contentConfig == null) {
            contentConfig = new ZCrawlerContentConfig();
            ruleConfig.setZCrawlerContentConfig(contentConfig);
        }
        if (crawlerContentDTO.getField() == null || crawlerContentDTO.getField().isEmpty()) {
            contentConfig.setFieldConfigList(null);
        } else {
            List<ZCrawlerFieldConfig> fieldConfigs = crawlerContentDTO.getField().stream()
                    .map((config)->{
                        try {
                            ZCrawlerFieldConfig fieldConfig =
                                    JSONObject.parseObject(Base64.getDecoder().decode(config), ZCrawlerFieldConfig.class);
                            return fieldConfig;
                        } catch (Exception e) {
                            log.error("field parse error " + config, e);
                        }
                        return null;
                    })
                    .filter(x -> x != null)
                    .collect(Collectors.toList());
            contentConfig.setFieldConfigList(fieldConfigs);
        }
        crawlerRule.setzCrawlerRuleConfig(ruleConfig);
        int rst = zeusCrawlerRuleManager.addOrUpdateZrawlerRule(crawlerRule);
        if (rst > 0) {
            return Result.success(crawlerRule.getId());
        } else {
            return Result.failed(ErrorCode.SYSTEM_ERROR.getCode(), "更新数据库错误");
        }
    }

    /**
     * 将field信息base64编码
     * @param fieldDTO
     * @return
     */
    @RequestMapping("/crawler/field/base64")
    @ResponseBody
    public String base64EncodeField(
            FieldDTO fieldDTO
            ) {
        if (StringUtils.isEmpty(fieldDTO.getFieldName())
                || StringUtils.isEmpty(fieldDTO.getBelongPage())
                || StringUtils.isEmpty(fieldDTO.getFieldRule())) {
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "参数错误,请查看参数后重试");
        }
        ZCrawlerFieldConfig fieldConfig = new ZCrawlerFieldConfig();
        fieldConfig.setName(fieldDTO.getFieldName());
        fieldConfig.setRule(fieldDTO.getFieldRule());
//        if (StringUtils.isEmpty(finalMerge)) {
//            finalMerge = PregUtil.joinContentMatch(fieldRule);
//        }
        if (StringUtils.isNotEmpty(fieldDTO.getFinalMerge())) {
            fieldConfig.setFinalMerge(fieldDTO.getFinalMerge());
        }
        fieldConfig.setBelongPage(fieldDTO.getBelongPage());
        fieldConfig.setMulti(fieldDTO.getMulti());
        String rst = JSONObject.toJSONString(fieldConfig);
        Base64.Encoder encoder = Base64.getEncoder();
        String rstData = new String(encoder.encode(rst.getBytes()));
        return Result.success(rstData);
    }


    /**
     * task相关
     * 保存或者更新任务
     * @return
     */
    @PostMapping(value = "/addorupdate.do")
    @ResponseBody
    public String taskAddOrUpdateAction(
            TaskDTO taskDTO
    ) {
        ZTask task = new ZTask();
        task.settName(taskDTO.getTaskName());
        task.setId(taskDTO.getTaskId());
        task.settAuto(taskDTO.getAuto());
        int rst = 0;
        if (StringUtils.isNotEmpty(taskDTO.getCronTime())) {
            task.setCron(taskDTO.getCronTime());
            if (!CronExpression.isValidExpression(taskDTO.getCronTime())) {
                log.info("user cron expression error " + taskDTO.getCronTime());
                return Result.failed(ErrorCode.OTHER_ERROR_CRON_INVALID.getCode(), "任务时间配置错误,请书信后重试");
            }
        }
        if (taskDTO.getTaskId() == null || taskDTO.getTaskId() == 0) {
            if (StringUtils.isEmpty(taskDTO.getTaskName())) {
                return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "taskName 不能为空");
            }
            int groupId = 0;
            if (zeusTaskManager.countTask(taskDTO.getTaskName(), groupId) > 0) {
                return Result.failed(ErrorCode.DB_ERROR_DUPLICATE.getCode(), "任务名称已经存在");
            }
            rst = zeusTaskManager.insert(task);
            if (0 == rst) {
                return Result.failed(ErrorCode.DB_ERROR_FAILED.getCode(), "数据库插入失败");
            }
        } else{
            task.setGmtModified(System.currentTimeMillis());
            rst = zeusTaskManager.update(task);
            if (0 == rst) {
                return Result.failed(ErrorCode.DB_ERROR_FAILED.getCode(), "数据库更新失败");
            }
        }


        return Result.success(task.getId());
    }

    /**
     * 删除任务
     * @param taskDTO
     * @return
     */
    @RequestMapping("/delete.do")
    @ResponseBody
    public String taskDeleteAction(
            TaskDTO taskDTO
    ) {
        if (taskDTO.getTaskId() == null || taskDTO.getTaskId() == 0 ){
            return  Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "taskId 为空");
        }
        int rst = zeusTaskManager.deleteTaskById(taskDTO.getTaskId());
        if (rst > 0) {
            return Result.success();
        }
        return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "taskId 错误或者已经被删除");
    }

    /**
     * run collect
     * @return
     */
    @RequestMapping(value = "/collect/{taskId}")
    @ResponseBody
    public ParallelFlux<String> collect(@PathVariable Integer taskId) {
        if (taskId == 0) {
            return Flux.just("error: params error").parallel();
        }
        ZTask ztask = zeusTaskManager.getTaskById(taskId);
        if (ztask == null) {
            return Flux.just("error: params error").parallel();
        }
        try {
           return zeusTaskManager.collectForController(taskId).parallel();
        } catch (Exception e) {
            log.error("collect error " + taskId, e);
            JSONObject object = new JSONObject();
            object.put("type", "error");
            object.put("msg", e.getMessage());
            return Flux.just(object.toJSONString()).parallel();
        }
    }

    /**
     *
     * demo
     * run collect
     * @return
     */
    @RequestMapping(value = "/retrieve/{taskId}")
    @ResponseBody
    public ParallelFlux<String> demo(@PathVariable Integer taskId) {
        System.out.println(taskId);
        return Flux.just(0, 1).map(x-> {
            Random r = new Random();
            int y = r.nextInt(10);
            System.out.println("sleep " + y);
            try {
                Thread.sleep( y * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return String.valueOf(y + 10); }).parallel();
    }

    private ZCrawlerRule checkAndGetRule(Integer taskId, Integer ruleId) throws Exception {
        //TODO 走cache
        if ((taskId == null || taskId == 0) && (ruleId == null || ruleId == 0)) {
            throw new Exception("params error");
        }
        ZCrawlerRule crawlerRule = null;
        if(ruleId == null || ruleId == 0) {
            crawlerRule = zeusCrawlerRuleManager.getCrawlerRuleByTaskId(taskId);
            if (crawlerRule == null) {
                crawlerRule = new ZCrawlerRule();
                crawlerRule.setTaskId(taskId);
            }
        } else {
            crawlerRule = crawlerRule = zeusCrawlerRuleManager.getCrawlerRulById(ruleId);
            if (crawlerRule == null) {
                throw new Exception("rule params error");
            }
            if (taskId != null && taskId > 0) {
                if (!taskId.equals(crawlerRule.getTaskId())) {
                    throw new Exception("task params error");
                }
            }
        }
        return crawlerRule;
    }
}
