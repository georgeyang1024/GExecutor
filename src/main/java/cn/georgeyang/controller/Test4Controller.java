package cn.georgeyang.controller;

import cn.georgeyang.apibean.OrderPayPriceApiBean;
import cn.georgeyang.executor.GExecutorService;
import cn.georgeyang.mapper.GoodEntityMapper;
import cn.georgeyang.mapper.OrderEntityMapper;
import cn.georgeyang.mapper.RsOrderGoodEntityMapper;
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
 * 计算订单总价
 * 对比测试
 */
@Controller
@ResponseBody
@RequestMapping("/test4")
public class Test4Controller {
    @Resource
    OrderEntityMapper orderEntityMapper;
    @Autowired
    GExecutorService GExecutorService;
    @Autowired
    GoodEntityMapper goodEntityMapper;
    @Autowired
    RsOrderGoodEntityMapper rsOrderGoodEntityMapper;

    @ResponseBody
    @RequestMapping("/m1")
    public List<OrderPayPriceApiBean> m1(HttpServletRequest request) throws Exception {
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderPayPriceApiBean> retList = new ArrayList<>(list.size());
        for (OrderEntity orderEntity : list) {
            OrderPayPriceApiBean apiBean = new OrderPayPriceApiBean();
            BeanUtils.copyProperties(orderEntity, apiBean);
            //goodList
            RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
            rsOrderGoodEntityExample.createCriteria().andOrderIdEqualTo(orderEntity.getId());
            List<RsOrderGoodEntity> rsOrderGoodEntities = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);
            Double payPrice = 0D;
            if (Utils.isNotEmpty(rsOrderGoodEntities))
                for (RsOrderGoodEntity rsOrderGoodEntity : rsOrderGoodEntities) {
                    if (rsOrderGoodEntity != null && rsOrderGoodEntity.getPayPrice() != null)
                        payPrice += rsOrderGoodEntity.getPayPrice();
                }
            apiBean.userTotalPayPrice = payPrice;
            retList.add(apiBean);
        }
        return retList;
    }

    @ResponseBody
    @RequestMapping("/d1")
    public List<OrderPayPriceApiBean> d1(HttpServletRequest request) throws Exception {
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        return GExecutorService.tranToList(OrderPayPriceApiBean.class, list);
    }



}
