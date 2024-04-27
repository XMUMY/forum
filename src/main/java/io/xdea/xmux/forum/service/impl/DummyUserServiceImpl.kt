package io.xdea.xmux.forum.service.impl

import io.xdea.xmux.forum.service.UserService
import io.xdea.xmux.forum.service.UserService.AuthResult
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*

@Service
@Profile("dev", "test")
class DummyUserServiceImpl : UserService {
    override fun auth(token: String?): AuthResult {
        var token = token
        if (token.isNullOrEmpty()) {
            return AuthResult(null, false)
        }

        if (!token.startsWith("basic ")) {
            System.out.printf("Dummy auth: token is '%s'\n", token)
            return AuthResult("cst1709364", true)
        }

        token = token.substring("basic ".length).trim { it <= ' ' }
        val decodedToken = Base64.getDecoder().decode(token)
        val userInfo = String(decodedToken).split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (userInfo.size != 2) {
            println("Dummy auth: BASIC auth received, but data is not correct")
        } else {
            System.out.printf(
                "Dummy auth: BASIC auth received, username is %s, password is %s\n",
                userInfo[0],
                userInfo[1]
            )
        }
        return AuthResult("cst1709364", true)
    }
}
