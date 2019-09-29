package cn.georgeyang.exec;

import cn.georgeyang.executor.*;
import cn.georgeyang.mapper.OrderEntityMapper;
import cn.georgeyang.mapper.RsOrderGoodEntityMapper;
import cn.georgeyang.pojo.*;
import cn.georgeyang.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class Exec_GetUserBuyGoodIdListByUId extends NormSubExecImpl<Object, Integer, List<Integer>> {

    @Override
    public List<Integer> onSubSelect(ExecuteContext executeContext, Class<List<Integer>> returnType, Integer param, Object attachParam) throws Exception {
        RsOrderGoodEntityMapper rsOrderGoodEntityMapper = webApplicationContext.getBean(RsOrderGoodEntityMapper.class);
        OrderEntityMapper orderEntityMapper = webApplicationContext.getBean(OrderEntityMapper.class);
        List<Integer> goodList = new ArrayList<>();

        OrderEntityExample orderEntityExample = new OrderEntityExample();
        orderEntityExample.createCriteria().andUserIdEqualTo(param);
        List<OrderEntity> orderList = orderEntityMapper.selectByExample(orderEntityExample);
        for (OrderEntity orderEntity : orderList) {
            RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
            rsOrderGoodEntityExample.createCriteria().andOrderIdEqualTo(orderEntity.getId());
            List<RsOrderGoodEntity> rsOrderGoodList = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);
            if (Utils.isEmpty(rsOrderGoodList))
                continue;
            for (RsOrderGoodEntity rsOrderGoodEntity : rsOrderGoodList) {
                //如果有重复，这里可以自定义缓存并避免重复查询
                GoodEntityWithBLOBs goodEntityWithBLOBs = null;
                if (executeContext != null) {
                    goodEntityWithBLOBs = (GoodEntityWithBLOBs) executeContext.getContextCache(GExecutorService,GoodEntityWithBLOBs.class,rsOrderGoodEntity.getGoodId());
                    if (goodEntityWithBLOBs == null) {
                        goodEntityWithBLOBs = GExecutorService.fetch(executeContext,GoodEntityWithBLOBs.class,rsOrderGoodEntity.getGoodId());
                        if (goodEntityWithBLOBs != null)
                            executeContext.putContextCache(GExecutorService,GoodEntityWithBLOBs.class,false,rsOrderGoodEntity.getGoodId(),goodEntityWithBLOBs);
                    }
                } else {
                    goodEntityWithBLOBs = GExecutorService.fetch(null,GoodEntityWithBLOBs.class,rsOrderGoodEntity.getGoodId());
                }

                if (goodEntityWithBLOBs != null)
                    goodList.add(rsOrderGoodEntity.getGoodId());
            }
        }


        return goodList;
    }

    @Override
    public List<Integer> onSubSelectMore(ExecuteContext executeContext, Class<List<Integer>> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }
}
