package com.huanshare.test.business;

import com.huanshare.test.business.interceptor.HttpInterceptor;
import com.huanshare.test.business.interceptor.TeamInterceptor;
import com.huanshare.swagger.core.EnableMandaoSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = {"com.huanshare.test.business", "com.huanshare.test.client.common.support.base.service", "com.huanshare.test.business.aop"})
@EnableDiscoveryClient
@EnableMandaoSwagger
@EnableFeignClients(basePackages = {"com.huanshare.test.user.client"})
@EnableTransactionManagement
@EnableScheduling// 启动定时任务
public class ServerApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Configuration
    public class StaticResourceConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
    }


    @Bean
    public TeamInterceptor teamInterceptor() {
        return new TeamInterceptor();
    }

    /**
     * 装载拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(teamInterceptor()).addPathPatterns("/**");
    }
}