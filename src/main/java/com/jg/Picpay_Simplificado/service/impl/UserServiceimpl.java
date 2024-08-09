package com.jg.Picpay_Simplificado.service.impl;

import com.jg.Picpay_Simplificado.dto.TransferRequest;
import com.jg.Picpay_Simplificado.dto.UserRequest;
import com.jg.Picpay_Simplificado.handler.InsuficentBalanceHandler;
import com.jg.Picpay_Simplificado.mapper.UserMapper;
import com.jg.Picpay_Simplificado.model.User;
import com.jg.Picpay_Simplificado.model.UserType;
import com.jg.Picpay_Simplificado.repository.UserRepository;
import com.jg.Picpay_Simplificado.service.AuthorizationService;
import com.jg.Picpay_Simplificado.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceimpl implements UserService {
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
    private final UserMapper userMapper;

    public UserServiceimpl(UserRepository userRepository, AuthorizationService authorizationService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity makeTransaction(TransferRequest transferRequest) {
        Optional<User> userTransfering = userRepository.findById(transferRequest.payer());
        Optional<User> userReceiving = userRepository.findById(transferRequest.payee());

        if (userTransfering.isEmpty() || userReceiving.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        else if (userTransfering.get().getUserType() == UserType.LOJISTA){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lojistas não podem transferir dinheiro");
        }
        else if (userTransfering.get().getSaldo() < transferRequest.value()){
            throw new InsuficentBalanceHandler();
        }

        userReceiving.get().setSaldo(userReceiving.get().getSaldo() + transferRequest.value());
        userTransfering.get().setSaldo(userTransfering.get().getSaldo() - transferRequest.value());

        if (authorizationService.isAuthorized()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Transação não autorizada pelo sistema");
        }

        userRepository.save(userReceiving.get());
        userRepository.save(userTransfering.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
               "value: " + transferRequest.value() +
                "\npayee: " + transferRequest.payee() +
                "\npayer: " + transferRequest.payer()
        );
    }

    @Override
    public ResponseEntity registerUser(UserRequest userRequest) {
        userRepository.save(userMapper.convertToEntity(userRequest));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario cadastrado no sistema");
    }
}
