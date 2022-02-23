package io.xdea.xmux.forum.service.impl;

import io.xdea.xmux.forum.service.UserService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import xmux.core.auth.v4.Auth;
import xmux.core.auth.v4.AuthInternalGrpc;

import java.util.Base64;

@Service
@Profile("prod")
public class XmuxUserServiceImpl implements UserService {
    @GrpcClient(AuthInternalGrpc.SERVICE_NAME)
    private AuthInternalGrpc.AuthInternalBlockingStub authBlockingStub;

    @Override
    public AuthResult auth(String token) {
        if (token == null || !token.startsWith("basic ")) {
            return new AuthResult(null, false);
        }
        token = token.substring("basic ".length()).trim();
        byte[] decodedToken = Base64.getDecoder().decode(token);
        String[] userInfo = new String(decodedToken).split(":");
        if (userInfo.length != 2) {
            return new AuthResult(null, false);
        }
        final var authUserResp = authBlockingStub.authUser(Auth.AuthUserReq
                .newBuilder().setCampusIdPassword(
                        Auth.CampusIdPasswordCredential.newBuilder()
                                .setCampusId(userInfo[0])
                                .setPassword(userInfo[1]))
                .build());
        return new AuthResult(authUserResp.getUid(), true);
    }
}
