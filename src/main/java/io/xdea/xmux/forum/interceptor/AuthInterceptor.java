package io.xdea.xmux.forum.interceptor;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.sentry.Sentry;
import io.xdea.xmux.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthInterceptor implements ServerInterceptor {
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
            if (!authResult.success()) {
                serverCall.close(Status.UNAUTHENTICATED, new Metadata());
                return new ServerCall.Listener<ReqT>() {
                };
            }

            User user = new User(authResult.uid(), "", Collections.emptyList());
            UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
            return Contexts.interceptCall(Context.current(), serverCall, metadata, next);
        } catch (Exception e) {
            Sentry.captureException(e);
            serverCall.close(Status.UNAUTHENTICATED, new Metadata());
            return new ServerCall.Listener<ReqT>() {
            };
        }
    }

    public static String getUid() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof User user) {
                return user.getUsername();
            }
        }
        throw new RuntimeException("Invalid authentication information");
    }
}
