package cn.georgeyang.apibean;

import cn.georgeyang.exec.Exec_GetOrderTotalPayPriceByOrderId;
import cn.georgeyang.exec.Exec_GetRsOrderGoodListByOrderId;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.OrderEntity;
import cn.georgeyang.pojo.UserEntity;

import java.util.List;

public class OrderInfoApiBean extends OrderEntity {
    @ExecutableField(bindFieldName = "userId")
    public UserEntity userEntity;

    //多层
    @ExecutableField(bindFieldName = "id",executeImpl = Exec_GetRsOrderGoodListByOrderId.class,forceDataInner = true)
    public List<RsOrderGoodApiBean> relationShipList;

    @ExecutableField(bindFieldName = "id",executeImpl = Exec_GetOrderTotalPayPriceByOrderId.class)
    public Double userTotalPayPrice;
}
