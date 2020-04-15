package com.zzq.user.mapper;

import com.zzq.mybatis.annotation.Mapper;
import com.zzq.mybatis.annotation.Select;
import com.zzq.mybatis.core.BaseMapper;
import com.zzq.user.model.Msg;

import java.util.List;

@Mapper
public interface TDao {

    @Select("select * from msg")
    public List<Msg> list();

    @Select("select * from msg where id = {id} and user_id = {userid}")
    public List<Msg> getByIdAndUserId(String id, String userid);


}
