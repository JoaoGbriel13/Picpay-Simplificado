package com.jg.Picpay_Simplificado.controller;

import com.jg.Picpay_Simplificado.dto.TransferRequest;
import com.jg.Picpay_Simplificado.dto.UserRequest;
import com.jg.Picpay_Simplificado.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/transaction")
    public ResponseEntity makeTransaction(@RequestBody TransferRequest transferRequest){
        return userService.makeTransaction(transferRequest);
    }
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserRequest userRequest){
        return userService.registerUser(userRequest);
    }
}
