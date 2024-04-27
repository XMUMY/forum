package io.xdea.xmux.forum.service.impl

import io.xdea.xmux.forum.service.UserService
import io.xdea.xmux.forum.service.UserService.AuthResult
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import xmux.core.auth.v4.Auth
import xmux.core.auth.v4.AuthInternalGrpc
import xmux.core.auth.v4.AuthInternalGrpc.AuthInternalBlockingStub
import java.util.*

@Service
@Profile("prod")
class XmuxUserServiceImpl : UserService {
    @GrpcClient(AuthInternalGrpc.SERVICE_NAME)
    private val authBlockingStub: AuthInternalBlockingStub? = null

    override fun auth(token: String?): AuthResult {
        var token = token
        if (token == null || !token.startsWith("basic ")) {
            return AuthResult(null, false)
        }
        token = token.substring("basic ".length).trim { it <= ' ' }
        val decodedToken = Base64.getDecoder().decode(token)
        val userInfo = String(decodedToken).split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (userInfo.size != 2) {
            return AuthResult(null, false)
        }
        val authUserResp = authBlockingStub!!.authUser(
            Auth.AuthUserReq
                .newBuilder().setCampusIdPassword(
                    Auth.CampusIdPasswordCredential.newBuilder()
                        .setCampusId(userInfo[0])
                        .setPassword(userInfo[1])
                )
                .build()
        )
        return AuthResult(authUserResp.uid, true)
    }
}
