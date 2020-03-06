package com.zzq.mybatis.mapper;

import com.zzq.mybatis.annotation.Mapper;
import com.zzq.mybatis.annotation.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Select("select* from user_info")
    public List getAll();

    @Select("select* from user_info where uid={id}")
    public List getById(String id);

}
