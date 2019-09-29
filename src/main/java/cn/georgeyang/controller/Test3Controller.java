package cn.georgeyang.controller;

import cn.georgeyang.apibean.OrderUserApiBean;
import cn.georgeyang.bean.OutBean;
import cn.georgeyang.executor.GExecutorService;
import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.mapper.OrderEntityMapper;
import cn.georgeyang.mapper.UserEntityMapper;
import cn.georgeyang.pojo.OrderEntity;
import cn.georgeyang.pojo.OrderEntityExample;
import cn.georgeyang.pojo.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/test3")
public class Test3Controller {
    @Resource
    UserEntityMapper userEntityMapper;
    @Resource
    OrderEntityMapper orderEntityMapper;
    @Autowired
    GExecutorService GExecutorService;

    @ResponseBody
    @RequestMapping("/m1")
    public List<OrderUserApiBean> m1(HttpServletRequest request) throws Exception{
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderUserApiBean> retList = new ArrayList<>(list.size());
        for (OrderEntity orderEntity : list) {
            OrderUserApiBean apiBean = new OrderUserApiBean();
            BeanUtils.copyProperties(orderEntity,apiBean);
            UserEntity userEntity = userEntityMapper.selectByPrimaryKey(orderEntity.getUserId());
            apiBean.userEntity = userEntity;
            retList.add(apiBean);
        }
        return retList;
    }

    @ResponseBody
    @RequestMapping("/d1")
    public List<OrderUserApiBean> d1(HttpServletRequest request) throws Exception{
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        return GExecutorService.tranToList(OrderUserApiBean.class,list);
    }

    @ResponseBody
    @RequestMapping("/d2")
    public OutBean d2(HttpServletRequest request) throws Exception{
        ExecuteContext executeContext = new ExecuteContext();
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderUserApiBean> retList = GExecutorService.tranToList(executeContext, OrderUserApiBean.class,list);
        return new OutBean(retList,executeContext.getDataMap());
    }


}
