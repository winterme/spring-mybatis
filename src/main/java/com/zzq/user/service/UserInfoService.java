package com.zzq.user.service;

import com.zzq.mybatis.core.BaseService;
import com.zzq.user.model.UserInfo;
import com.zzq.user.model.UserInfoVo;

import java.util.List;

public interface UserInfoService extends BaseService<UserInfo> {

    List<UserInfo> getByUserName(String username);

    List<UserInfoVo> getUserInfoVoByUsername(String username);

}
