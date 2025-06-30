//package app.security.authentication
//
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.userdetails.UserDetails
//
//class TokenAuthentication(private var token: String) : Authentication {
//
//    private var isAuthenticated = false
//    private var userDetails: UserDetails? = null
//
//    override fun getAuthorities(): Collection<GrantedAuthority>? {
//        return userDetails?.authorities
//    }
//
//    override fun getCredentials(): Any? {
//        return null
//    }
//
//    override fun getDetails(): Any? {
//        return userDetails
//    }
//
//    fun setDetails(userDetails: UserDetails) {
//        this.userDetails = userDetails
//    }
//
//    override fun getPrincipal(): Any? {
//        return userDetails
//    }
//
//    override fun isAuthenticated(): Boolean {
//        return isAuthenticated
//    }
//
//    override fun setAuthenticated(isAuthenticated: Boolean) {
//        this.isAuthenticated = isAuthenticated
//    }
//
//    override fun getName(): String {
//        return token
//    }
//}