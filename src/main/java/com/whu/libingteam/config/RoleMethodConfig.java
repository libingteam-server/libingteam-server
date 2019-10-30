package com.whu.libingteam.config;

import cc.eamon.open.permission.mvc.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class RoleMethodConfig extends WebMvcConfigurerAdapter {

    @Bean
    public RoleMethodChecker roleMethodChecker(){
        return new RoleMethodChecker();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionInterceptor(roleMethodChecker()))
                .addPathPatterns("/**");

        super.addInterceptors(registry);
    }

}
