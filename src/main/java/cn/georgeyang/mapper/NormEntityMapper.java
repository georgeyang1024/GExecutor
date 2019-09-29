package cn.georgeyang.mapper;

import cn.georgeyang.pojo.NormEntity;
import cn.georgeyang.pojo.NormEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NormEntityMapper {
    int countByExample(NormEntityExample example);

    int deleteByExample(NormEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NormEntity record);

    int insertSelective(NormEntity record);

    List<NormEntity> selectByExample(NormEntityExample example);

    NormEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NormEntity record, @Param("example") NormEntityExample example);

    int updateByExample(@Param("record") NormEntity record, @Param("example") NormEntityExample example);

    int updateByPrimaryKeySelective(NormEntity record);

    int updateByPrimaryKey(NormEntity record);
}