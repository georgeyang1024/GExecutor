package cn.georgeyang.exec;

import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.pojo.RsOrderGoodEntity;
import cn.georgeyang.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据RsGoodNormId获取这个规格有多少个订单
 */
public class Exec_GetGoodNormOrderCount extends ExecBaseGoodNormEarn<Object, Integer, Integer> {
    @Override
    public Integer onSubSelect(ExecuteContext executeContext, Class<Integer> returnType, Integer param, Object attachParam) throws Exception {
        List<RsOrderGoodEntity> list = super.getRsOrderGoodListCache(executeContext,param);
        if (Utils.isEmpty(list))
            return 0;
        List<Long> orderIdList = new ArrayList<>();
        for (RsOrderGoodEntity rsOrderGoodEntity : list) {
            if (!orderIdList.contains(rsOrderGoodEntity.getOrderId()))
                orderIdList.add(rsOrderGoodEntity.getOrderId());
        }
        return orderIdList.size();
    }

    @Override
    public Integer onSubSelectMore(ExecuteContext executeContext, Class<Integer> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }

}
