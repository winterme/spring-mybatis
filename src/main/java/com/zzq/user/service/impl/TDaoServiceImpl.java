package com.zzq.user.service.impl;

import com.zzq.mybatis.core.BaseServiceImpl;
import com.zzq.user.mapper.TDao;
import com.zzq.user.model.Msg;
import com.zzq.user.service.TDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TDaoServiceImpl extends BaseServiceImpl<Msg> implements TDaoService {

    @Autowired
    private TDao tDao;

    @Override
    public List list() {
        return tDao.list();
    }
}
