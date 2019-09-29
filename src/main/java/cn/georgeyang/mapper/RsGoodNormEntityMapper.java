package cn.georgeyang.mapper;

import cn.georgeyang.pojo.RsGoodNormEntity;
import cn.georgeyang.pojo.RsGoodNormEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RsGoodNormEntityMapper {
    int countByExample(RsGoodNormEntityExample example);

    int deleteByExample(RsGoodNormEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RsGoodNormEntity record);

    int insertSelective(RsGoodNormEntity record);

    List<RsGoodNormEntity> selectByExample(RsGoodNormEntityExample example);

    RsGoodNormEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RsGoodNormEntity record, @Param("example") RsGoodNormEntityExample example);

    int updateByExample(@Param("record") RsGoodNormEntity record, @Param("example") RsGoodNormEntityExample example);

    int updateByPrimaryKeySelective(RsGoodNormEntity record);

    int updateByPrimaryKey(RsGoodNormEntity record);
}