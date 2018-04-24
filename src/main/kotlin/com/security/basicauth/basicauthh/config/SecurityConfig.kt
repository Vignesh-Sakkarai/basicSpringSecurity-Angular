package com.security.basicauth.basicauthh.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter(){

    /**
     * In memory Auth with hardcoded username, password and authorities
     */
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.inMemoryAuthentication()?.
                withUser("admin")?.password("{noop}admin")?.authorities("ADMIN", "USER")
                ?.and()
                ?.withUser("user")?.password("{noop}user")?.authorities("USER")
    }

    /**
     * Response for preflight has invalid HTTP status code 401.
     **/
    @Bean
    @Throws(Exception::class)
    fun corsFilter(): CorsFilter {
        return CorsFilter()
    }

    override fun configure(http: HttpSecurity?) {
        // starts authorizing configurations
            http?.authorizeRequests()
                ?.antMatchers("/index.html", "/app/app.js", "/css/**")?.permitAll()
                // following URL is only accessible for USER users
                ?.antMatchers("/api/user")?.hasAuthority("USER")
                // following URL is only accessible for ADMIN AND USER users
                ?.antMatchers("/api/admin")?.hasAnyAuthority("USER", "ADMIN")?.anyRequest()?.fullyAuthenticated()
                ?.and()
                // enabling the basic authentication
                ?.httpBasic()?.and()
                // no session in the server
                ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)?.and()
                //Response for preflight has invalid HTTP status code 401.
                ?.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter::class.java)
                // disabling the CSRF - Cross Site Request Forgery
                ?.csrf()?.disable()
        }

}