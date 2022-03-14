package io.xdea.xmux.forum.config;

import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class GeneralConfig {
    private final AuthInterceptor authInterceptor;

    @Autowired
    public GeneralConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Bean
    @DependsOn("authInterceptor")
    public GrpcServerConfigurer interceptorServerConfigurer() {
        return serverBuilder -> {
            serverBuilder.intercept(authInterceptor);
        };
    }

    @Bean
    public String[] orderingStrList() {
        return new String[]{"update_at desc", "likes desc", "posts desc", "last_update desc", "create_at asc"};
    }
}
