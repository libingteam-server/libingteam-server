package com.whu.libingteam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan("com.whu.libingteam")
@EnableTransactionManagement
@MapperScan("com.whu.libingteam.*.dao")
@EnableSwagger2
public class LibingteamApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibingteamApplication.class, args);
    }

}
