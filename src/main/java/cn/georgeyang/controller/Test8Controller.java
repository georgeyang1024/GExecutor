package cn.georgeyang.controller;

import cn.georgeyang.apibean.UserBuyGoodListApiBean;
import cn.georgeyang.bean.OutBean;
import cn.georgeyang.executor.GExecutorService;
import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.mapper.GoodEntityMapper;
import cn.georgeyang.mapper.OrderEntityMapper;
import cn.georgeyang.mapper.RsOrderGoodEntityMapper;
import cn.georgeyang.mapper.UserEntityMapper;
import cn.georgeyang.pojo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取用户购买过的商品
 * 对比测试
 */
@Controller
@ResponseBody
@RequestMapping("/test8")
public class Test8Controller {
    @Resource
    UserEntityMapper userEntityMapper;
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
    public List<UserBuyGoodListApiBean> m1(HttpServletRequest request) throws Exception{
        UserEntityExample userEntityExample = new UserEntityExample();
        List<UserEntity> userList = userEntityMapper.selectByExample(userEntityExample);
        List<UserBuyGoodListApiBean> retList = new ArrayList<>();
        for (UserEntity userEntity : userList) {
            Map<Integer,GoodEntityWithBLOBs> buyGoodMap = new HashMap<>();

            OrderEntityExample orderEntityExample = new OrderEntityExample();
            orderEntityExample.createCriteria().andUserIdEqualTo(userEntity.getId());
            List<OrderEntity> orderList = orderEntityMapper.selectByExample(orderEntityExample);
            for (OrderEntity orderEntity : orderList) {
                RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
                rsOrderGoodEntityExample.createCriteria().andOrderIdEqualTo(orderEntity.getId());
                List<RsOrderGoodEntity> rsOrderGoodList = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);
                for (RsOrderGoodEntity rsOrderGoodEntity : rsOrderGoodList) {
                    if (!buyGoodMap.containsKey(rsOrderGoodEntity.getGoodId())) {
                        GoodEntityWithBLOBs goodEntityWithBLOBs = goodEntityMapper.selectByPrimaryKey(rsOrderGoodEntity.getGoodId());
                        buyGoodMap.put(rsOrderGoodEntity.getGoodId(),goodEntityWithBLOBs);
                    }
                }
            }

            UserBuyGoodListApiBean apiBean = new UserBuyGoodListApiBean();
            BeanUtils.copyProperties(userEntity,apiBean);
            apiBean.buyGoodList = new ArrayList(buyGoodMap.values());
            retList.add(apiBean);
        }
        return retList;
    }

    @ResponseBody
    @RequestMapping("/d1")
    public List<UserBuyGoodListApiBean> d1(HttpServletRequest request) throws Exception{
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<UserBuyGoodListApiBean> retList = GExecutorService.tranToList(UserBuyGoodListApiBean.class,list);
        return retList;
    }

    @ResponseBody
    @RequestMapping("/d2")
    public OutBean d2(HttpServletRequest request) throws Exception{
        ExecuteContext executeContext = new ExecuteContext();
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<UserBuyGoodListApiBean> retList = GExecutorService.tranToList(executeContext,UserBuyGoodListApiBean.class,list);
        return new OutBean(retList,executeContext.getDataMap());
    }


}
