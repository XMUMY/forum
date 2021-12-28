package com.flyingblu.community.interceptor;

import io.grpc.*;

public class AuthInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> next) {
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(next.startCall(serverCall, metadata)) {
            @Override
            public void onMessage(ReqT request) {
                // TODO: make request have a interface to check token
                super.onMessage(request);
            }
        };
    }
}
