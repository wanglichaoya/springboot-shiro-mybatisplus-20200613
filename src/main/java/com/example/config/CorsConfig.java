package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2020/6/13
 * Time: 下午 10:11
 * Description:全局跨域处理
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     * Configure cross origin requests processing.
     * 配置跨域请求
     *
     * @param registry
     * @since 4.2
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
