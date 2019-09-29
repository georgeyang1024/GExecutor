package cn.georgeyang.exec;

import cn.georgeyang.executor.*;
import cn.georgeyang.mapper.RsOrderGoodEntityMapper;
import cn.georgeyang.pojo.GoodEntityWithBLOBs;
import cn.georgeyang.pojo.RsOrderGoodEntity;
import cn.georgeyang.pojo.RsOrderGoodEntityExample;
import cn.georgeyang.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据订单id获取商品名称列表
 */
public class Exec_GetOrderGoodNameListByOrderId extends NormSubExecImpl<Object, Long, List<String>> {


    @Override
    public List<String> onSubSelect(ExecuteContext executeContext, Class<List<String>> returnType, Long param, Object attachParam) throws Exception {
        RsOrderGoodEntityMapper rsOrderGoodEntityMapper = webApplicationContext.getBean(RsOrderGoodEntityMapper.class);
        RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
        rsOrderGoodEntityExample.createCriteria().andOrderIdEqualTo(param);
        List<RsOrderGoodEntity> rsOrderGoodEntities = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);
        if (Utils.isEmpty(rsOrderGoodEntities))
            return null;
        List<String> nameList = new ArrayList<>(rsOrderGoodEntities.size());
        for (RsOrderGoodEntity rsOrderGoodEntity : rsOrderGoodEntities) {
            GoodEntityWithBLOBs goodEntityWithBLOBs = GExecutorService.fetch(executeContext, GoodEntityWithBLOBs.class,rsOrderGoodEntity.getGoodId());
            nameList.add(goodEntityWithBLOBs.getGoodName());
        }
        return nameList;
    }

    @Override
    public List<String> onSubSelectMore(ExecuteContext executeContext, Class<List<String>> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }
}
