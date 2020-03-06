package com.zzq.mybatis.test;

import com.zzq.mybatis.PkuSession;
import com.zzq.mybatis.mapper.TDao;
import com.zzq.mybatis.mapper.UserInfoMapper;

public class MRun {

    public static void main(String[] args) {

        PkuSession pkuSession = new PkuSession();

        TDao dao = pkuSession.getMapper(TDao.class);

        System.out.println(dao.getClass());

        System.out.println(dao.getById("6aa3bf27-1a6a-4ddc-a386-4b343eaf77ae" , "123"));

        System.out.println(dao);

        System.out.println(pkuSession.getMapper(UserInfoMapper.class).getById("559e4329-b6e4-4f38-90b4-6197aaaaba6c"));


    }

}
