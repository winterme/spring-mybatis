package com.zzq.user.service;

import com.zzq.mybatis.core.BaseService;
import com.zzq.user.model.Msg;

import java.util.List;

public interface TDaoService extends BaseService<Msg> {

    List list();

}
