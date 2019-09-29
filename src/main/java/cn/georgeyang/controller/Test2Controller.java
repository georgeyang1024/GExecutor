package cn.georgeyang.controller;

import cn.georgeyang.apibean.OrderUserApiBean;
import cn.georgeyang.apibean.UserEntityApiBean;
import cn.georgeyang.bean.OutBean;
import cn.georgeyang.executor.GExecutorService;
import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.mapper.OrderEntityMapper;
import cn.georgeyang.mapper.UserEntityMapper;
import cn.georgeyang.pojo.OrderEntity;
import cn.georgeyang.pojo.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
@RequestMapping("/test2")
public class Test2Controller {
    @Resource
    UserEntityMapper userEntityMapper;
    @Resource
    OrderEntityMapper orderEntityMapper;
    @Autowired
    GExecutorService GExecutorService;

    @ResponseBody
    @RequestMapping("/m1")
    public OrderUserApiBean m1(HttpServletRequest request) throws Exception{
        OrderUserApiBean apiBean = new OrderUserApiBean();
        OrderEntity orderEntity = orderEntityMapper.selectByPrimaryKey(1L);
        BeanUtils.copyProperties(orderEntity,apiBean);
        UserEntity userEntity = userEntityMapper.selectByPrimaryKey(orderEntity.getUserId());
        apiBean.userEntity = userEntity;
        return apiBean;
    }

    @ResponseBody
    @RequestMapping("/d1")
    public OrderUserApiBean d1(HttpServletRequest request) throws Exception{
        return GExecutorService.select(OrderUserApiBean.class,1L);
    }

    @ResponseBody
    @RequestMapping("/d2")
    public OutBean d2(HttpServletRequest request) throws Exception{
        ExecuteContext executeContext = new ExecuteContext();
        OrderUserApiBean apiBean = GExecutorService.fetch(executeContext, OrderUserApiBean.class,1L);
        return new OutBean(apiBean,executeContext.getDataMap());
    }

    @ResponseBody
    @RequestMapping("/m3")
    public UserEntityApiBean m3(HttpServletRequest request) throws Exception{
        String userId = request.getParameter("userId");
        Integer userIdInt = Integer.valueOf(userId);

        String loginUserId = request.getParameter("loginUserId");
        Integer loginUserIdInt = Integer.valueOf(loginUserId);

        UserEntity userEntity = userEntityMapper.selectByPrimaryKey(userIdInt);
        UserEntityApiBean apiBean = new UserEntityApiBean();
        BeanUtils.copyProperties(userEntity,apiBean);
        apiBean.isSelf = loginUserIdInt == userIdInt;
        return apiBean;
    }

    @ResponseBody
    @RequestMapping("/d3")
    public UserEntityApiBean d3(HttpServletRequest request) throws Exception{
        String userId = request.getParameter("userId");
        Integer uid = Integer.valueOf(userId);

        String loginUserId = request.getParameter("loginUserId");
        Integer loginUserIdInt = Integer.valueOf(loginUserId);

        return GExecutorService.fetch(null,UserEntityApiBean.class,uid,loginUserIdInt);
    }
}
