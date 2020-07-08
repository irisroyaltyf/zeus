package top.yulegou.zeus.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.yulegou.zeus.dao.domain.ZConfig;
import top.yulegou.zeus.dao.domain.ZConfigExample;
@Mapper
public interface ZConfigMapper {
    long countByExample(ZConfigExample example);

    int deleteByExample(ZConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ZConfig record);

    int insertSelective(ZConfig record);

    List<ZConfig> selectByExampleWithBLOBs(ZConfigExample example);

    List<ZConfig> selectByExample(ZConfigExample example);

    ZConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZConfig record, @Param("example") ZConfigExample example);

    int updateByExampleWithBLOBs(@Param("record") ZConfig record, @Param("example") ZConfigExample example);

    int updateByExample(@Param("record") ZConfig record, @Param("example") ZConfigExample example);

    int updateByPrimaryKeySelective(ZConfig record);

    int updateByPrimaryKeyWithBLOBs(ZConfig record);

    int updateByPrimaryKey(ZConfig record);
}