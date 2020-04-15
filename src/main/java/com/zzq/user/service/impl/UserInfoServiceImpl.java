package com.zzq.user.service.impl;

import com.zzq.mybatis.core.BaseServiceImpl;
import com.zzq.user.mapper.UserInfoMapper;
import com.zzq.user.model.UserInfo;
import com.zzq.user.model.UserInfoVo;
import com.zzq.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> getByUserName(String username) {
        return userInfoMapper.getByUserName(username);
    }

    @Override
    public List<UserInfoVo> getUserInfoVoByUsername(String username) {
        return userInfoMapper.getUserInfoVoByUsername(username);
    }
}
