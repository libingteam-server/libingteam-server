package com.whu.libingteam.config;

import cc.eamon.open.permission.mvc.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RoleMethodConfig implements WebMvcConfigurer {

    private boolean isDebug = true;

    @Bean
    public RoleMethodChecker roleMethodChecker(){
        return new RoleMethodChecker();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(!isDebug)
        registry.addInterceptor(new PermissionInterceptor(roleMethodChecker()))
                .addPathPatterns("/**");
    }

}
