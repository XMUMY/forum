package io.xdea.xmux.forum.service;

public interface UserService {
    AuthResult auth(String token);

    record AuthResult(String uid, boolean success) {
    }
}
