package cn.georgeyang.exec;

import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.pojo.RsGoodNormEntity;
import cn.georgeyang.pojo.RsOrderGoodEntity;
import cn.georgeyang.utils.Utils;

import java.util.List;

/**
 * 根据RsGoodNormId获取这个规格总单价
 */
public class Exec_GetGoodNormTotalCostPrice extends ExecBaseGoodNormEarn<Object, Integer, Double> {

    @Override
    public Double onSubSelect(ExecuteContext executeContext, Class<Double> returnType, Integer param, Object attachParam) throws Exception {
        List<RsOrderGoodEntity> list = super.getRsOrderGoodListCache(executeContext,param);
        if (Utils.isEmpty(list))
            return 0d;
        RsGoodNormEntity rsGoodNormEntity = GExecutorService.fetch(executeContext,RsGoodNormEntity.class,param);
        Double rsGoodNormCostPrice = rsGoodNormEntity.getCostPrice();
        if (rsGoodNormCostPrice == null)
            return 0d;
        Double retPrice = 0d;
        for (RsOrderGoodEntity rsOrderGoodEntity : list) {
            retPrice += rsGoodNormCostPrice * rsOrderGoodEntity.getBuySum();
        }
        return retPrice;
    }

    @Override
    public Double onSubSelectMore(ExecuteContext executeContext, Class<Double> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }
}
