package com.jg.Picpay_Simplificado.service;

import com.jg.Picpay_Simplificado.dto.TransferDTO;
import com.jg.Picpay_Simplificado.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity makeTransaction(TransferDTO transferDTO);
    public ResponseEntity registerUser(UserDTO userRequest);
}
