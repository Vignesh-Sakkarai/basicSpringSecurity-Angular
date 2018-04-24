package com.security.basicauth.basicauthh.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class AppController{

    @GetMapping("/user")
    fun sayHello(principal: Principal): Principal{
        return principal
    }
}