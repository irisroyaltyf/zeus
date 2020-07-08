package top.yulegou.zeus.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.yulegou.zeus.dao.domain.ZPublishRule;
import top.yulegou.zeus.dao.domain.ZPublishRuleExample;

@Mapper
public interface ZPublishRuleMapper {
    long countByExample(ZPublishRuleExample example);

    int deleteByExample(ZPublishRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ZPublishRule record);

    int insertSelective(ZPublishRule record);

    List<ZPublishRule> selectByExampleWithBLOBs(ZPublishRuleExample example);

    List<ZPublishRule> selectByExample(ZPublishRuleExample example);

    ZPublishRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZPublishRule record, @Param("example") ZPublishRuleExample example);

    int updateByExampleWithBLOBs(@Param("record") ZPublishRule record, @Param("example") ZPublishRuleExample example);

    int updateByExample(@Param("record") ZPublishRule record, @Param("example") ZPublishRuleExample example);

    int updateByPrimaryKeySelective(ZPublishRule record);

    int updateByPrimaryKeyWithBLOBs(ZPublishRule record);

    int updateByPrimaryKey(ZPublishRule record);
}