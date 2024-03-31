package org.scau.config;

import org.scau.imterceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor interceptor;

    // 添加全局拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).excludePathPatterns("/user/login", "/user/register", "/pics/**", "/removeImg");
    }

    // 图片保存路径
    public static String path = System.getProperty("user.dir") + "/../pics/";

    // 映射static路径的请求到其他目录下
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取运行时路径
        registry.addResourceHandler("/pics/**").addResourceLocations("file:" + path);
    }
}
