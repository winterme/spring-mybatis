package com.zzq;

import com.zzq.user.mapper.TDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MyBatisTest extends Tester {

    @Autowired
    private TDao tDao;

    @Test
    public void m1(){
        for (Object o : tDao.list()) {
            System.out.println(o);
        }
    }

}
