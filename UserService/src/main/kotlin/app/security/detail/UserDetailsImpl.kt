package app.security.detail

import app.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserDetailsImpl(private val user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> =
        Collections.singletonList(SimpleGrantedAuthority(user.role.name))

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.email

    fun getName(): String = user.name

    fun getSurname(): String = user.surname

    fun getEmail(): String = user.email

    fun getId(): UUID? = user.id

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}