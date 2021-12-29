package com.flyingblu.community.service.impl;

import com.flyingblu.community.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class XmuxUserServiceImpl implements UserService {

    @Override
    public AuthResult auth(String token) {
        System.out.println("TODO: Implement com.flyingblu.community.service.impl.XmuxUserServiceImpl! ");
        return null;
    }
}
