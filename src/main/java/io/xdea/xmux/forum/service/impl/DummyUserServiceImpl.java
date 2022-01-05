package io.xdea.xmux.forum.service.impl;

import io.xdea.xmux.forum.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "test"})
public class DummyUserServiceImpl implements UserService {
    @Override
    public AuthResult auth(String token) {
        if (token != null && token.equals("valid_token"))
            return new AuthResult("developer", true);
        return new AuthResult(null, false);
    }
}
