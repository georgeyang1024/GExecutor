package cn.georgeyang.mapper;

import cn.georgeyang.pojo.RsOrderGoodEntity;
import cn.georgeyang.pojo.RsOrderGoodEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RsOrderGoodEntityMapper {
    int countByExample(RsOrderGoodEntityExample example);

    int deleteByExample(RsOrderGoodEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RsOrderGoodEntity record);

    int insertSelective(RsOrderGoodEntity record);

    List<RsOrderGoodEntity> selectByExample(RsOrderGoodEntityExample example);

    RsOrderGoodEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RsOrderGoodEntity record, @Param("example") RsOrderGoodEntityExample example);

    int updateByExample(@Param("record") RsOrderGoodEntity record, @Param("example") RsOrderGoodEntityExample example);

    int updateByPrimaryKeySelective(RsOrderGoodEntity record);

    int updateByPrimaryKey(RsOrderGoodEntity record);
}