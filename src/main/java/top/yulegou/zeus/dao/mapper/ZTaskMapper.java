package top.yulegou.zeus.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.dao.domain.ZTaskExample;

@Mapper
public interface ZTaskMapper {
    long countByExample(ZTaskExample example);

    int deleteByExample(ZTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ZTask record);

    int insertSelective(ZTask record);

    List<ZTask> selectByExampleWithBLOBs(ZTaskExample example);

    List<ZTask> selectByExample(ZTaskExample example);

    ZTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZTask record, @Param("example") ZTaskExample example);

    int updateByExampleWithBLOBs(@Param("record") ZTask record, @Param("example") ZTaskExample example);

    int updateByExample(@Param("record") ZTask record, @Param("example") ZTaskExample example);

    int updateByPrimaryKeySelective(ZTask record);

    int updateByPrimaryKeyWithBLOBs(ZTask record);

    int updateByPrimaryKey(ZTask record);

    int updateStatus(Map<String, Object> params);

}