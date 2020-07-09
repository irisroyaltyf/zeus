package top.yulegou.zeus.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.yulegou.zeus.dao.domain.ZTaskCollected;
import top.yulegou.zeus.dao.domain.ZTaskCollectedExample;

@Mapper
public interface ZTaskCollectedMapper {
    long countByExample(ZTaskCollectedExample example);

    int deleteByExample(ZTaskCollectedExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ZTaskCollected record);

    int insertSelective(ZTaskCollected record);

    List<ZTaskCollected> selectByExample(ZTaskCollectedExample example);

    ZTaskCollected selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZTaskCollected record, @Param("example") ZTaskCollectedExample example);

    int updateByExample(@Param("record") ZTaskCollected record, @Param("example") ZTaskCollectedExample example);

    int updateByPrimaryKeySelective(ZTaskCollected record);

    int updateByPrimaryKey(ZTaskCollected record);
}