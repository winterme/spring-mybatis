package com.zzq.user.controller;

import com.zzq.user.mapper.TDao;
import com.zzq.user.model.Msg;
import com.zzq.user.model.UserInfo;
import com.zzq.user.service.TDaoService;
import com.zzq.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;

@Controller
public class GetDataController {

    @Autowired
    private TDaoService tDaoService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/getData")
    @ResponseBody
    public Object getData(){
        return tDaoService.list();
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(){
        Msg msg = new Msg();
        msg.setId(UUID.randomUUID().toString());
        msg.setMsg("==========");
        msg.setReplyTime(new Date());

        return tDaoService.save(msg);
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object get(String id){

        return tDaoService.getById(id);
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public Object getAll(){

        return tDaoService.getAll();
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo(String id){

        return userInfoService.getById(id);
    }

    @RequestMapping("/getAllUserInfo")
    @ResponseBody
    public Object getAllUserInfo(){

        return userInfoService.getAll();
    }


    @RequestMapping("/getUserInfoByEntity")
    @ResponseBody
    public Object getUserInfoByEntity(UserInfo userInfo){

        return userInfoService.getByEntity(userInfo);
    }



}
