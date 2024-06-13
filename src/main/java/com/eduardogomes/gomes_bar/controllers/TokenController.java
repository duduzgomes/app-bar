package com.eduardogomes.gomes_bar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.eduardogomes.gomes_bar.dto.LoginRequest;
import com.eduardogomes.gomes_bar.dto.LoginResponse;
import com.eduardogomes.gomes_bar.services.UserService;

@RestController
public class TokenController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception{
        var token = userService.login(loginRequest);
        return ResponseEntity.ok().body(token);
    } 
}
