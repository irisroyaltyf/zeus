package top.yulegou.zeus.crawler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.dao.domain.*;
import top.yulegou.zeus.domain.ContentCollectedDTO;
import top.yulegou.zeus.manager.ZeusConfigManager;
import top.yulegou.zeus.manager.http.HttpExecutorManager;
import top.yulegou.zeus.util.PregUtil;

import java.util.*;

@Slf4j
@Component
public class ContentCrawler extends BaseCrawler {
    @Autowired
    private HttpExecutorManager httpExecutorManager;
    //TODO如果 循环入库则 返回多条数据,如果
    public ContentCollectedDTO collectField(String url, ZTask task, ZCrawlerContentConfig contentConfig, boolean transferImage) {
        // TODO URL 判断是否重复抓取, 还是可以重复抓取 表collected
        // TODO 如果需要的话 sleep一段时间按
        ContentCollectedDTO contentCollectedDTO = new ContentCollectedDTO();
        contentCollectedDTO.setUrl(url);
        contentCollectedDTO.setTaskId(task.getId());
        if (StringUtils.isEmpty(url) || (!StringUtils.startsWithIgnoreCase(url, "http:")
                && !StringUtils.startsWithIgnoreCase(url, "https"))) {
            log.error("网址不完善");
        }
        if (contentConfig == null
                || contentConfig.getFieldConfigList() == null
                || contentConfig.getFieldConfigList().isEmpty()) {
            log.info("内容抓取设置有误");
            return contentCollectedDTO;
        }
        Map<String, String> headers = new HashMap<>();
        if (task.getzTaskConfig() != null && StringUtils.isNotBlank(task.getzTaskConfig().getUserAgent())) {
            headers.put(ZeusConfigManager.USER_AGENT, task.getzTaskConfig().getUserAgent());
        }
        if (task.getzTaskConfig() != null && StringUtils.isNotBlank(task.getzTaskConfig().getRefer())) {
            headers.put(ZeusConfigManager.REFER, task.getzTaskConfig().getRefer());
        }
        String contentHtml;
        if (contentConfig.isPost()) {
            contentHtml = httpExecutorManager.doPost(url, headers, null);
        } else {
            contentHtml = httpExecutorManager.doGet(url, headers);
        }
        if (StringUtils.isEmpty(contentHtml)) {
            log.error("抓取内容页错误 url: " + url);
            return contentCollectedDTO;
        }
        for (ZCrawlerFieldConfig fieldConfig: contentConfig.getFieldConfigList()) {
            if (fieldConfig.getType() == 1) {
                List<String> f = pregAndMatch(contentHtml, PregUtil.pregConvertMatch(fieldConfig.getRule()), fieldConfig.getFinalMerge());
                //TODO 现在只是用逗号链接起来
                String fieldRst = StringUtils.join(f, ",");
                contentCollectedDTO.addFieldResult(fieldConfig.getName(), StringUtils.join(f, ","));
                if (transferImage) {
                    /**
                     * 替换的过程仅仅是 replace 如果页面显示的话也会被替换
                     */
                    contentCollectedDTO.addImages(fieldConfig.getName(), PregUtil.matchImgSrcs(fieldRst));
                }
            }
        }
        contentCollectedDTO.setSuccess(true);
        return  contentCollectedDTO;
    }
}
