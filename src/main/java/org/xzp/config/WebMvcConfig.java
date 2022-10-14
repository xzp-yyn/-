package org.xzp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/9 15:13
 * @Version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 处理静态资源路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/static/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/static/front/");
        registry.addResourceHandler("/common/**").addResourceLocations("classpath:/static/common/");
    }
}
