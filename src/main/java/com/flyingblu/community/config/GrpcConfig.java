package com.flyingblu.community.config;

import com.flyingblu.community.interceptor.AuthInterceptor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {
    @Bean
    public GrpcServerConfigurer interceptorServerConfigurer() {
        return serverBuilder -> {
            serverBuilder.intercept(new AuthInterceptor());
        };
    }
}
