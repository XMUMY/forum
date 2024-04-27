package io.xdea.xmux.forum.config

import io.envoyproxy.pgv.ReflectiveValidatorIndex
import io.envoyproxy.pgv.grpc.ValidatingServerInterceptor
import io.grpc.ServerBuilder
import io.xdea.xmux.forum.interceptor.AuthInterceptor
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class GeneralConfig {
    @Bean
    open fun ValidatingServerInterceptor(): ValidatingServerInterceptor {
        val index = ReflectiveValidatorIndex()
        return ValidatingServerInterceptor(index)
    }

    @Bean
    @Autowired
    open fun interceptorServerConfigurer(
        authInterceptor: AuthInterceptor?,
        validatingServerInterceptor: ValidatingServerInterceptor?
    ): GrpcServerConfigurer {
        return GrpcServerConfigurer { serverBuilder: ServerBuilder<*> ->
            serverBuilder.intercept(authInterceptor)
            serverBuilder.intercept(validatingServerInterceptor)
        }
    }

    @Bean
    open fun orderingStrList(): Array<String> {
        return arrayOf("last_update desc", "likes desc", "posts desc", "create_at desc", "create_at asc")
    }
}
