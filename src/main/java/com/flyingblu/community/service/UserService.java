package com.flyingblu.community.service;

public interface UserService {
    public AuthResult auth(String token);

    public static class AuthResult {
        public String uid;
        public boolean success;

        public AuthResult(String uid, boolean success) {
            this.uid = uid;
            this.success = success;
        }
    }
}
