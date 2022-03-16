package io.xdea.xmux.forum.config;

import io.grpc.Status;
import io.sentry.Sentry;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class SentryGrpcAdvice {
    @GrpcExceptionHandler(Exception.class)
    public Status handleAllException(Exception e) {
        e.printStackTrace();
        Sentry.captureException(e);
        return Status.INTERNAL.withDescription("Unknown error").withCause(e);
    }
}
