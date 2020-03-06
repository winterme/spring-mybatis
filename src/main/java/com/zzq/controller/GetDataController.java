package com.zzq.controller;

import com.zzq.mybatis.mapper.TDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetDataController {

    @Autowired
    private TDao tDao;

    @RequestMapping("/getData")
    @ResponseBody
    public Object getData(){
        return tDao.list();
    }

}
