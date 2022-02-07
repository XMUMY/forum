package io.xdea.xmux.forum.interceptor;

import io.sentry.Sentry;
import io.xdea.xmux.forum.service.UserService;
import io.grpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthInterceptor implements ServerInterceptor {
    public static final Context.Key<String> UID = Context.key("uid");
    private final UserService userService;

    @Autowired
    public AuthInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> next) {
        try {
            String token = metadata.get(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER));
            var authResult = userService.auth(token);
            if (!authResult.success) {
                serverCall.close(Status.UNAUTHENTICATED, new Metadata());
                return new ServerCall.Listener<ReqT>() {
                };
            }
            // Propagate UID to controllers
            Context context = Context.current().withValue(UID, authResult.uid);
            return Contexts.interceptCall(context, serverCall, metadata, next);
        } catch (Exception e) {
            Sentry.captureException(e);
            serverCall.close(Status.UNAUTHENTICATED, new Metadata());
            return new ServerCall.Listener<ReqT>() {
            };
        }
    }
}
