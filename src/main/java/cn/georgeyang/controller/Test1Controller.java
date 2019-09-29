package cn.georgeyang.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.georgeyang.executor.GExecutorService;
import cn.georgeyang.mapper.UserEntityMapper;
import cn.georgeyang.pojo.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户信息操作Controller
 * @author panzh
 *
 */
@Controller
@ResponseBody
@RequestMapping("/test")
public class Test1Controller {
    @Resource
    UserEntityMapper userEntityMapper;
    @Autowired
    GExecutorService GExecutorService;

    @ResponseBody
    @RequestMapping("/m1")
    public UserEntity m1(HttpServletRequest request) throws Exception{
        return userEntityMapper.selectByPrimaryKey(1);
    }

    @ResponseBody
    @RequestMapping("/d1")
    public UserEntity d1(HttpServletRequest request) throws Exception{
        return GExecutorService.select(UserEntity.class,1);
    }

    @ResponseBody
    @RequestMapping("/m2")
    public UserEntity m2(HttpServletRequest request) throws Exception{
        String userId = request.getParameter("userId");
        Integer uid = Integer.valueOf(userId);
        return userEntityMapper.selectByPrimaryKey(uid);
    }

    @ResponseBody
    @RequestMapping("/d2")
    public UserEntity d2(HttpServletRequest request) throws Exception{
        String userId = request.getParameter("userId");
        Integer uid = Integer.valueOf(userId);
        return GExecutorService.select(UserEntity.class,uid);
    }




}
