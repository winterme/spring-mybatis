package com.zzq.mybatis.mapper;

import com.zzq.mybatis.annotation.Mapper;
import com.zzq.mybatis.annotation.Select;

import java.util.List;

@Mapper
public interface TDao {

    @Select("select * from msg")
    public List list();

    @Select("select * from msg where id = {id} and user_id = {userid}")
    public List getById(String id, String userid);



}
