package com.zzq;

import com.zzq.mybatis.annotation.MapperScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScanner("com.zzq.user.mapper")
@SpringBootApplication // spring 启动注解
public class Application {

    public static void main(String[] args) {
        SpringApplication.run( Application.class , args );
    }

}
