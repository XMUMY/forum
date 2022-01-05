package io.xdea.xmux.forum.service.impl;

import io.xdea.xmux.forum.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class XmuxUserServiceImpl implements UserService {

    @Override
    public AuthResult auth(String token) {
        System.out.println("TODO: Implement io.xdea.xmux.forum.service.impl.XmuxUserServiceImpl! ");
        return null;
    }
}
