package cn.georgeyang.mapper;

import cn.georgeyang.pojo.OrderEntity;
import cn.georgeyang.pojo.OrderEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderEntityMapper {
    int countByExample(OrderEntityExample example);

    int deleteByExample(OrderEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderEntity record);

    int insertSelective(OrderEntity record);

    List<OrderEntity> selectByExample(OrderEntityExample example);

    OrderEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderEntity record, @Param("example") OrderEntityExample example);

    int updateByExample(@Param("record") OrderEntity record, @Param("example") OrderEntityExample example);

    int updateByPrimaryKeySelective(OrderEntity record);

    int updateByPrimaryKey(OrderEntity record);
}