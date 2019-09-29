package cn.georgeyang.exec;

import cn.georgeyang.executor.*;
import cn.georgeyang.mapper.RsOrderGoodEntityMapper;
import cn.georgeyang.pojo.GoodEntityWithBLOBs;
import cn.georgeyang.pojo.RsOrderGoodEntity;
import cn.georgeyang.pojo.RsOrderGoodEntityExample;
import cn.georgeyang.utils.Utils;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;


/**
 * 根据订单id获取订单总价
 */
public class Exec_GetOrderTotalPayPriceByOrderId extends NormSubExecImpl<Object, Long, Double> {

    @Override
    public Double onSubSelect(ExecuteContext executeContext, Class<Double> returnType, Long orderId, Object attachParam) throws Exception {
        RsOrderGoodEntityMapper rsOrderGoodEntityMapper = webApplicationContext.getBean(RsOrderGoodEntityMapper.class);
        //goodList
        RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
        rsOrderGoodEntityExample.createCriteria().andOrderIdEqualTo(orderId);
        List<RsOrderGoodEntity> rsOrderGoodEntities = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);
        Double payPrice = 0D;
        if (Utils.isNotEmpty(rsOrderGoodEntities))
            for (RsOrderGoodEntity rsOrderGoodEntity : rsOrderGoodEntities) {
                if (rsOrderGoodEntity != null && rsOrderGoodEntity.getPayPrice() != null)
                    payPrice += rsOrderGoodEntity.getPayPrice();
            }
        return payPrice;
    }

    @Override
    public Double onSubSelectMore(ExecuteContext executeContext, Class<Double> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }
}
