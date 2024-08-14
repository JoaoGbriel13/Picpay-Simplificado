package com.jg.Picpay_Simplificado.controller;

import com.jg.Picpay_Simplificado.dto.TransferDTO;
import com.jg.Picpay_Simplificado.dto.UserDTO;
import com.jg.Picpay_Simplificado.service.UserService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity makeTransaction(@RequestBody TransferDTO transferDTO){
        return userService.makeTransaction(transferDTO);
    }
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDTO userRequest){
        return userService.registerUser(userRequest);
    }
}
