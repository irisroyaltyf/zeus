package top.yulegou.zeus.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.yulegou.zeus.dao.domain.ZCrawlerRule;
import top.yulegou.zeus.dao.domain.ZCrawlerRuleExample;

@Mapper
public interface ZCrawlerRuleMapper {
    long countByExample(ZCrawlerRuleExample example);

    int deleteByExample(ZCrawlerRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ZCrawlerRule record);

    int insertSelective(ZCrawlerRule record);

    List<ZCrawlerRule> selectByExampleWithBLOBs(ZCrawlerRuleExample example);

    List<ZCrawlerRule> selectByExample(ZCrawlerRuleExample example);

    ZCrawlerRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZCrawlerRule record, @Param("example") ZCrawlerRuleExample example);

    int updateByExampleWithBLOBs(@Param("record") ZCrawlerRule record, @Param("example") ZCrawlerRuleExample example);

    int updateByExample(@Param("record") ZCrawlerRule record, @Param("example") ZCrawlerRuleExample example);

    int updateByPrimaryKeySelective(ZCrawlerRule record);

    int updateByPrimaryKeyWithBLOBs(ZCrawlerRule record);

    int updateByPrimaryKey(ZCrawlerRule record);
}