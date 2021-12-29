package com.flyingblu.community.config;

import com.flyingblu.community.interceptor.AuthInterceptor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class GrpcConfig {
    private final AuthInterceptor authInterceptor;

    @Autowired
    public GrpcConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Bean
    @DependsOn("authInterceptor")
    public GrpcServerConfigurer interceptorServerConfigurer() {
        return serverBuilder -> {
            serverBuilder.intercept(authInterceptor);
        };
    }
}
