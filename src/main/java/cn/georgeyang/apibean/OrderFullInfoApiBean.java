package cn.georgeyang.apibean;

import cn.georgeyang.exec.Exec_GetOrderTotalPayPriceByOrderId;
import cn.georgeyang.exec.Exec_GetRsOrderGoodListByOrderId;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.OrderEntity;
import cn.georgeyang.pojo.UserEntity;

import java.util.List;

public class OrderFullInfoApiBean extends OrderEntity {
    @ExecutableField(bindFieldName = "userId")
    public UserEntity userEntity;

    @ExecutableField(bindFieldName = "id",executeImpl = Exec_GetRsOrderGoodListByOrderId.class,forceDataInner = true)
    public List<RsOrderGoodNormApiBean> relationShipList;

    @ExecutableField(bindFieldName = "id",executeImpl = Exec_GetOrderTotalPayPriceByOrderId.class)
    public Double userTotalPayPrice;

}
