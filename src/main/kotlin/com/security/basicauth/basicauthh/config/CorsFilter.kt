package com.security.basicauth.basicauthh.config
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.FilterChain
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.http.HttpServletRequest

/**
 * Response for preflight has invalid HTTP status code 401.
 **/
class CorsFilter : OncePerRequestFilter() {

    companion object {
        internal val ORIGIN = "Origin"
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain) {

        val origin = request.getHeader(ORIGIN)

        response.setHeader("Access-Control-Allow-Origin", "*")//* or origin as u prefer
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "content-type, authorization")

        if (request.getMethod().equals("OPTIONS"))
            response.status = HttpServletResponse.SC_OK
        else
            filterChain.doFilter(request, response)

    }

}