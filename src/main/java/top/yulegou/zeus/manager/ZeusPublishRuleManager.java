package top.yulegou.zeus.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.dao.domain.ZCrawlerRule;
import top.yulegou.zeus.dao.domain.ZPublishRule;
import top.yulegou.zeus.dao.domain.ZPublishRuleExample;
import top.yulegou.zeus.dao.mapper.ZPublishRuleMapper;

import java.util.Date;
import java.util.List;

@Component
public class ZeusPublishRuleManager {
    @Autowired
    private ZPublishRuleMapper zPublishRuleMapper;

    public ZPublishRule getPublishRuleByTaskId(Integer taskId) {
        ZPublishRuleExample example = new ZPublishRuleExample();
        ZPublishRuleExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(taskId);
        List<ZPublishRule> ruleList = zPublishRuleMapper.selectByExampleWithBLOBs(example);
        if (ruleList != null && !ruleList.isEmpty()) {
            return ruleList.get(0);
        }
        return null;
    }

    public ZPublishRule getPublishRulById(Integer crawlerId) {
        ZPublishRule publishRule = zPublishRuleMapper.selectByPrimaryKey(crawlerId);
        return publishRule;
    }

    public int addOrUpdateZPublishRule(ZPublishRule publishRule) {
        if (publishRule.getId() != null && publishRule.getId() > 0) {
            //update
            return zPublishRuleMapper.updateByPrimaryKeySelective(publishRule);
        } else {
            //insert
            Date now = new Date();
            publishRule.setGmtCreate(now.getTime());
            publishRule.setGmtModified(now.getTime());
            int rst = zPublishRuleMapper.insertSelective(publishRule);
            return rst;
        }
    }
    public int updateZPublishRule(ZPublishRule publishRule) {
        return zPublishRuleMapper.updateByPrimaryKeySelective(publishRule);
    }
}
