package cn.georgeyang.mapper;

import cn.georgeyang.pojo.GoodEntity;
import cn.georgeyang.pojo.GoodEntityExample;
import cn.georgeyang.pojo.GoodEntityWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodEntityMapper {
    int countByExample(GoodEntityExample example);

    int deleteByExample(GoodEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodEntityWithBLOBs record);

    int insertSelective(GoodEntityWithBLOBs record);

    List<GoodEntityWithBLOBs> selectByExampleWithBLOBs(GoodEntityExample example);

    List<GoodEntity> selectByExample(GoodEntityExample example);

    GoodEntityWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodEntityWithBLOBs record, @Param("example") GoodEntityExample example);

    int updateByExampleWithBLOBs(@Param("record") GoodEntityWithBLOBs record, @Param("example") GoodEntityExample example);

    int updateByExample(@Param("record") GoodEntity record, @Param("example") GoodEntityExample example);

    int updateByPrimaryKeySelective(GoodEntityWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GoodEntityWithBLOBs record);

    int updateByPrimaryKey(GoodEntity record);
}