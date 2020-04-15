package com.zzq.user.mapper;

import com.zzq.mybatis.annotation.Mapper;
import com.zzq.mybatis.annotation.Select;
import com.zzq.user.model.UserInfo;
import com.zzq.user.model.UserInfoVo;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Select("select* from user_info where username={username}")
    public List<UserInfo> getByUserName(String username);

    @Select("select * from msg m LEFT JOIN user_info u on m.user_id = u.username where u.username = {username}")
    public List<UserInfoVo> getUserInfoVoByUsername(String username);

}
