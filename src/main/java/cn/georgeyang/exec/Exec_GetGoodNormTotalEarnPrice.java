package cn.georgeyang.exec;

import cn.georgeyang.apibean.GoodNormEarnApiBean;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.pojo.RsGoodNormEntity;
import cn.georgeyang.pojo.RsOrderGoodEntity;
import cn.georgeyang.utils.Utils;

import java.util.List;

/**
 * 根据RsGoodNormId获取相关订单总盈利
 */
public class Exec_GetGoodNormTotalEarnPrice extends ExecBaseGoodNormEarn<Object, Integer, Double> {
    private GoodNormEarnApiBean goodNormEarnApiBean;

    @Override
    public boolean preStartSelect(ExecuteContext executeContext, ExecutableField executableField, Object tag) throws Exception {
        if (tag instanceof GoodNormEarnApiBean) {
            this.goodNormEarnApiBean = (GoodNormEarnApiBean) tag;
            return false;
        }
        return true;
    }

    @Override
    public Double onSubSelect(ExecuteContext executeContext, Class<Double> returnType, Integer param, Object attachParam) throws Exception {
        List<RsOrderGoodEntity> list = super.getRsOrderGoodListCache(executeContext,param);
        if (Utils.isEmpty(list))
            return 0d;
        RsGoodNormEntity rsGoodNormEntity = GExecutorService.fetch(executeContext,RsGoodNormEntity.class,param);
        Double rsGoodNormCostPrice = rsGoodNormEntity.getCostPrice();
        if (rsGoodNormCostPrice == null)
            return 0d;
        Double earnPrice = 0d;
        for (RsOrderGoodEntity rsOrderGoodEntity : list) {
            earnPrice += (rsOrderGoodEntity.getPayPrice() - rsGoodNormCostPrice * rsOrderGoodEntity.getBuySum());
        }
        return earnPrice;
    }

    @Override
    public Double onSubSelectMore(ExecuteContext executeContext, Class<Double> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }
}
