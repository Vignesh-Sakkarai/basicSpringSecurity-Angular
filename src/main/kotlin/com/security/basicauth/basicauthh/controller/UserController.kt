package com.security.basicauth.basicauthh.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController{

    @GetMapping("/resource")
    fun sayHelloToUser(): Map<String, Object>{
        val map = HashMap<String, Object>()
        map.put("message", "Hello User" as Object)
        map.put("timestamp", Date().time as Object)
        return map
    }
}