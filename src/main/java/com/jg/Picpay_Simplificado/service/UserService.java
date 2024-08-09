package com.jg.Picpay_Simplificado.service;

import com.jg.Picpay_Simplificado.dto.TransferRequest;
import com.jg.Picpay_Simplificado.dto.UserRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity makeTransaction(TransferRequest transferRequest);
    public ResponseEntity registerUser(UserRequest userRequest);
}
