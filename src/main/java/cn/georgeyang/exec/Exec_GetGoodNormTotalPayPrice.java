package cn.georgeyang.exec;

import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.pojo.RsOrderGoodEntity;
import cn.georgeyang.utils.Utils;

import java.util.List;

/**
 * 根据RsGoodNormId获取相关订单总支付总额
 */
public class Exec_GetGoodNormTotalPayPrice extends ExecBaseGoodNormEarn<Object, Integer, Double> {

    @Override
    public Double onSubSelect(ExecuteContext executeContext, Class<Double> returnType, Integer param, Object attachParam) throws Exception {
        List<RsOrderGoodEntity> list = super.getRsOrderGoodListCache(executeContext,param);
        if (Utils.isEmpty(list))
            return 0d;
        Double retPrice = 0d;
        for (RsOrderGoodEntity rsOrderGoodEntity : list) {
            retPrice += rsOrderGoodEntity.getPayPrice();
        }
        return retPrice;
    }

    @Override
    public Double onSubSelectMore(ExecuteContext executeContext, Class<Double> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }
}
