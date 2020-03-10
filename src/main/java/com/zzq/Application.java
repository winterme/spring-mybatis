package com.zzq;

import com.zzq.mybatis.annotation.MapperScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@MapperScanner("com.zzq.mybatis.mapper")
@SpringBootApplication // spring 启动注解
public class Application {

    public static void main(String[] args) {
        SpringApplication.run( Application.class , args );
    }

}
