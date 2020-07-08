package top.yulegou.zeus.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.dao.domain.ZCrawlerRule;
import top.yulegou.zeus.dao.domain.ZCrawlerRuleExample;
import top.yulegou.zeus.dao.mapper.ZCrawlerRuleMapper;

import java.util.Date;
import java.util.List;

@Component
public class ZeusCrawlerRuleManager {
    @Autowired
    private ZCrawlerRuleMapper zCrawlerRuleMapper;

    public ZCrawlerRule getCrawlerRuleByTaskId(Integer taskId) {
        ZCrawlerRuleExample example = new ZCrawlerRuleExample();
        ZCrawlerRuleExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(taskId);
        List<ZCrawlerRule> ruleList = zCrawlerRuleMapper.selectByExampleWithBLOBs(example);
        if (ruleList != null && !ruleList.isEmpty()) {
            return ruleList.get(0);
        }
        return null;
    }

    public ZCrawlerRule getCrawlerRulById(Integer crawlerId) {
        ZCrawlerRule crawlerRule = zCrawlerRuleMapper.selectByPrimaryKey(crawlerId);
        return crawlerRule;
    }

    public int addOrUpdateZrawlerRule(ZCrawlerRule crawlerRule) {
        if (crawlerRule.getId() != null && crawlerRule.getId() > 0) {
            //update
            return zCrawlerRuleMapper.updateByPrimaryKeySelective(crawlerRule);
        } else {
            //insert
            Date now = new Date();
            crawlerRule.setGmtCreate(now.getTime());
            crawlerRule.setGmtModified(now.getTime());
            int rst = zCrawlerRuleMapper.insertSelective(crawlerRule);
            return rst;
        }
    }
}
