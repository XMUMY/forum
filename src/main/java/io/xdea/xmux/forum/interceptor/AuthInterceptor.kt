package io.xdea.xmux.forum.interceptor

import io.grpc.*
import io.sentry.Sentry
import io.xdea.xmux.forum.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AuthInterceptor @Autowired constructor(private val userService: UserService) : ServerInterceptor {
    override fun <ReqT, RespT> interceptCall(
        serverCall: ServerCall<ReqT, RespT>,
        metadata: Metadata,
        next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        try {
            val token = metadata.get(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER))
            val authResult = userService.auth(token)
            if (!authResult.success) {
                serverCall.close(Status.UNAUTHENTICATED, Metadata())
                return object : ServerCall.Listener<ReqT>() {
                }
            }
            // Propagate UID to controllers
            val context = Context.current().withValue(UID, authResult.uid)
            return Contexts.interceptCall(context, serverCall, metadata, next)
        } catch (e: Exception) {
            Sentry.captureException(e)
            serverCall.close(Status.UNAUTHENTICATED, Metadata())
            return object : ServerCall.Listener<ReqT>() {
            }
        }
    }

    companion object {
        val UID: Context.Key<String> = Context.key("uid")
    }
}
