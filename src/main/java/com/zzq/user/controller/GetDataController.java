package com.zzq.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzq.user.mapper.TDao;
import com.zzq.user.model.Msg;
import com.zzq.user.model.UserInfo;
import com.zzq.user.model.UserInfoVo;
import com.zzq.user.service.TDaoService;
import com.zzq.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *  这里面全部是调用例子
 */
@Controller
public class GetDataController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TDaoService tDaoService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/getData")
    @ResponseBody
    public Object getData() throws JsonProcessingException {
        List<Msg> list = tDaoService.list();
        return objectMapper.writeValueAsString(list);
    }

    @RequestMapping("/getDataById")
    @ResponseBody
    public Object getDataById(String id, String userId) throws JsonProcessingException {
        List<Msg> list = tDaoService.getByIdAndUserId(id, userId);
        return objectMapper.writeValueAsString(list);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save() {
        Msg msg = new Msg();
        msg.setId(UUID.randomUUID().toString());
        msg.setMsg("==========");
        msg.setReplyTime(new Date());

        return tDaoService.save(msg);
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object get(String id) {

        return tDaoService.getById(id);
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public Object getAll() {

        return tDaoService.getAll();
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo(String id) {

        return userInfoService.getById(id);
    }

    @RequestMapping("/getAllUserInfo")
    @ResponseBody
    public Object getAllUserInfo() {

        return userInfoService.getAll();
    }


    @RequestMapping("/getUserInfoByEntity")
    @ResponseBody
    public Object getUserInfoByEntity(UserInfo userInfo) {

        List<UserInfo> list = userInfoService.getByEntity(userInfo);

        return list;
    }

    @RequestMapping("/getUserInfoByUserName")
    @ResponseBody
    public Object getUserInfoByUserName(String username) throws JsonProcessingException {
        List<UserInfo> userInfos = userInfoService.getByUserName(username);

        return objectMapper.writeValueAsString(userInfos);
    }

    @RequestMapping("/getUserInfoVoByUsername")
    @ResponseBody
    public Object getUserInfoVoByUsername(String username) throws JsonProcessingException {
        List<UserInfoVo> userInfos = userInfoService.getUserInfoVoByUsername(username);

        return objectMapper.writeValueAsString(userInfos);
    }


}
