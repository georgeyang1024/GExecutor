package cn.georgeyang.controller;

import cn.georgeyang.apibean.GoodNormEarnApiBean;
import cn.georgeyang.bean.OutBean;
import cn.georgeyang.exec.Exec_GetOrderNormInfoByGoodIdAndNormId;
import cn.georgeyang.executor.GExecutorService;
import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.mapper.*;
import cn.georgeyang.pojo.*;
import cn.georgeyang.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据商品id、规格信息(成本价)，算出所有订单总盈利
 * 对比测试
 */
@Controller
@ResponseBody
@RequestMapping("/test10")
public class Test10Controller {
    @Autowired
    GExecutorService GExecutorService;
    @Autowired
    GoodEntityMapper goodEntityMapper;
    @Autowired
    RsGoodNormEntityMapper rsGoodNormEntityMapper;
    @Autowired
    RsOrderGoodEntityMapper rsOrderGoodEntityMapper;
    @Resource
    NormEntityMapper normEntityMapper;

    @ResponseBody
    @RequestMapping("/m1")
    public List<GoodNormEarnApiBean> m1(HttpServletRequest request) throws Exception {
        List<GoodNormEarnApiBean> ret = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                RsGoodNormEntityExample example = new RsGoodNormEntityExample();
                example.createCriteria().andGoodIdEqualTo(i).andNormIdEqualTo(j);
                List<RsGoodNormEntity> list = rsGoodNormEntityMapper.selectByExample(example);
                if (Utils.isEmpty(list))
                    continue;
                for (RsGoodNormEntity rsGoodNormEntity : list) {
                    RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
                    rsOrderGoodEntityExample.createCriteria().andRsNormIdEqualTo(rsGoodNormEntity.getId());
                    List<RsOrderGoodEntity> rsOrderGoodList = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);
                    if (Utils.isEmpty(rsOrderGoodList))
                        continue;

                    GoodNormEarnApiBean bean = new GoodNormEarnApiBean();
                    BeanUtils.copyProperties(rsGoodNormEntity, bean);
                    bean.goodInfo = goodEntityMapper.selectByPrimaryKey(bean.getGoodId());
                    bean.normInfo = normEntityMapper.selectByPrimaryKey(bean.getNormId());

                    Double rsGoodNormCostPrice = rsGoodNormEntity.getCostPrice();
                    bean.totalPayPrice = 0D;
                    List<Long> orderList = new ArrayList<>();
                    for (RsOrderGoodEntity rsOrderGoodEntity : rsOrderGoodList) {
                        if (!orderList.contains(rsOrderGoodEntity.getOrderId())) {
                            orderList.add(rsOrderGoodEntity.getOrderId());
                        }
                        bean.totalCostPrice += rsGoodNormCostPrice * rsOrderGoodEntity.getBuySum();
                        bean.totalPayPrice += rsOrderGoodEntity.getPayPrice();
                    }
                    bean.orderCount = orderList.size();
                    bean.totalEarnPrice = bean.totalPayPrice - bean.totalCostPrice;
                    ret.add(bean);
                }
            }
        }

        return ret;
    }

    @ResponseBody
    @RequestMapping("/d1")
    public List<GoodNormEarnApiBean> d1(HttpServletRequest request) throws Exception {
        List<GoodNormEarnApiBean> ret = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Object[] params = new Object[]{i, j};
                List<GoodNormEarnApiBean> goodNormEarnApiBeanList = GExecutorService.execSubSelectMore(Exec_GetOrderNormInfoByGoodIdAndNormId.class, null, List.class, params,null);
                if (Utils.isEmpty(goodNormEarnApiBeanList))
                    continue;
                ret.addAll(goodNormEarnApiBeanList);
            }
        }
        return ret;
    }

    @ResponseBody
    @RequestMapping("/d2")
    public OutBean d2(HttpServletRequest request) throws Exception {
        ExecuteContext executeContext = new ExecuteContext();
        List<GoodNormEarnApiBean> ret = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Object[] params = new Object[]{i, j};
                List<GoodNormEarnApiBean> goodNormEarnApiBeanList = GExecutorService.execSubSelectMore(Exec_GetOrderNormInfoByGoodIdAndNormId.class, executeContext, List.class, params,null);
                if (Utils.isEmpty(goodNormEarnApiBeanList))
                    continue;
                ret.addAll(goodNormEarnApiBeanList);
            }
        }
        return new OutBean(ret, executeContext.getDataMap());
    }


}
