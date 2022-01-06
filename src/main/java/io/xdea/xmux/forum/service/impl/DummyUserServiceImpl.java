package io.xdea.xmux.forum.service.impl;

import io.xdea.xmux.forum.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "test"})
public class DummyUserServiceImpl implements UserService {
    @Override
    public AuthResult auth(String token) {
        if (token != null && !token.isEmpty()) {
            System.out.printf("Dummy auth: token is '%s'\n", token);
            return new AuthResult("cst1709364", true);
        }
        return new AuthResult(null, false);
    }
}
