package io.xdea.xmux.forum.service.impl;

import io.xdea.xmux.forum.service.UserService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import xmux.core.auth.v4.AuthGrpc;
import xmux.core.auth.v4.AuthOuterClass;

@Service
@Profile("prod")
public class XmuxUserServiceImpl implements UserService {
    @GrpcClient(AuthGrpc.SERVICE_NAME)
    private AuthGrpc.AuthBlockingStub authBlockingStub;

    @Override
    public AuthResult auth(String token) {
        final var authUserResp = authBlockingStub.authUser(AuthOuterClass.AuthUserReq
                .newBuilder().setFirebaseIdToken(token).build());
        return new AuthResult(authUserResp.getUid(), true);
    }
}
