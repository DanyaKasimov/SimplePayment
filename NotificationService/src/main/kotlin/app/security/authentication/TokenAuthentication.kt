package app.security.authentication

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class TokenAuthentication(
    private val token: String,
    private val principal: String,
    private val authorities: Collection<GrantedAuthority>
) : Authentication {

    private var authenticated: Boolean = true

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getCredentials(): Any? = null

    override fun getDetails(): Any? = null

    override fun getPrincipal(): Any = principal

    override fun isAuthenticated(): Boolean = authenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }

    override fun getName(): String = principal
}