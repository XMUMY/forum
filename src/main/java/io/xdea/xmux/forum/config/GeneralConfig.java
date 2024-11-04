package io.xdea.xmux.forum.config;

import io.envoyproxy.pgv.ReflectiveValidatorIndex;
import io.envoyproxy.pgv.grpc.ValidatingServerInterceptor;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class GeneralConfig {
    @Bean
    public ValidatingServerInterceptor ValidatingServerInterceptor() {
        ReflectiveValidatorIndex index = new ReflectiveValidatorIndex();
        return new ValidatingServerInterceptor(index);
    }

    @Bean
    @Autowired
    public GrpcServerConfigurer interceptorServerConfigurer(AuthInterceptor authInterceptor,
                                                            ValidatingServerInterceptor validatingServerInterceptor) {
        return serverBuilder -> {
            serverBuilder.intercept(authInterceptor);
            serverBuilder.intercept(validatingServerInterceptor);
        };
    }

    @Bean
    public String[] orderingStrList() {
        return new String[]{"last_update desc", "likes desc", "posts desc", "create_at desc", "create_at asc"};
    }
}
