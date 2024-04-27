package io.xdea.xmux.forum.service

interface UserService {
    fun auth(token: String?): AuthResult

    class AuthResult(val uid: String?, val success: Boolean)
}
