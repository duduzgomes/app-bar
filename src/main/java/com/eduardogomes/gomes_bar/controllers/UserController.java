package com.eduardogomes.gomes_bar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eduardogomes.gomes_bar.dto.LoginRequest;
import com.eduardogomes.gomes_bar.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Void> createUser(@RequestBody LoginRequest user) throws Exception{
       userService.createUser(user);
       return ResponseEntity.ok().build();
    }
}
