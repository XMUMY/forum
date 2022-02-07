package io.xdea.xmux.forum.service.impl;

import io.xdea.xmux.forum.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@Profile({"dev", "test"})
public class DummyUserServiceImpl implements UserService {
    @Override
    public AuthResult auth(String token) {
        if (token == null || token.isEmpty()) {
            return new AuthResult(null, false);
        }

        if (!token.startsWith("basic ")) {
            System.out.printf("Dummy auth: token is '%s'\n", token);
            return new AuthResult("cst1709364", true);
        }

        token = token.substring("basic ".length()).trim();
        byte[] decodedToken = Base64.getDecoder().decode(token);
        String[] userInfo = new String(decodedToken).split(":");
        if (userInfo.length != 2) {
            System.out.println("Dummy auth: BASIC auth received, but data is not correct");
        } else {
            System.out.printf("Dummy auth: BASIC auth received, username is %s, password is %s\n", userInfo[0], userInfo[1]);
        }
        return new AuthResult("cst1709364", true);
    }
}
