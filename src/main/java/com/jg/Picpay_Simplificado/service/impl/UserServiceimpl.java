package com.jg.Picpay_Simplificado.service.impl;

import com.jg.Picpay_Simplificado.dto.TransferDTO;
import com.jg.Picpay_Simplificado.dto.UserDTO;
import com.jg.Picpay_Simplificado.handler.InsuficentBalanceHandler;
import com.jg.Picpay_Simplificado.handler.UserNotAllowedException;
import com.jg.Picpay_Simplificado.handler.UserNotFoundException;
import com.jg.Picpay_Simplificado.mapper.TransferMapper;
import com.jg.Picpay_Simplificado.mapper.UserMapper;
import com.jg.Picpay_Simplificado.model.Transfer;
import com.jg.Picpay_Simplificado.model.User;
import com.jg.Picpay_Simplificado.model.UserType;
import com.jg.Picpay_Simplificado.repository.TransferRepository;
import com.jg.Picpay_Simplificado.repository.UserRepository;
import com.jg.Picpay_Simplificado.service.AuthorizationService;
import com.jg.Picpay_Simplificado.service.NotificationService;
import com.jg.Picpay_Simplificado.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceimpl implements UserService {
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final UserMapper userMapper;
    private final TransferRepository transferRepository;

    public UserServiceimpl(UserRepository userRepository, AuthorizationService authorizationService, NotificationService notificationService, UserMapper userMapper,
                           TransferRepository transferRepository) {
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
        this.userMapper = userMapper;
        this.transferRepository = transferRepository;
    }

    @Override
    @Transactional
    public ResponseEntity makeTransaction(TransferDTO transferDTO) {
        User userTransfering = userRepository.findById(transferDTO.payer()).orElseThrow(
                () -> new UserNotFoundException(transferDTO.payer()));
        User userReceiving = userRepository.findById(transferDTO.payee()).orElseThrow(
                () -> new UserNotFoundException(transferDTO.payee())
        );
        validateTransaction(userTransfering, transferDTO);

        userReceiving.setSaldo(userReceiving.getSaldo().add(transferDTO.value()));
        userTransfering.setSaldo(userTransfering.getSaldo().subtract(transferDTO.value()));
        Transfer transfer = new Transfer(transferDTO.value(),userTransfering, userReceiving);

        userRepository.save(userReceiving);
        userRepository.save(userTransfering);
        transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transfer));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                "value: " + transferDTO.value() +
                        "\npayee: " + transferDTO.payee() +
                        "\npayer: " + transferDTO.payer()
        );
    }

    @Override
    public ResponseEntity registerUser(UserDTO userRequest) {
        userRepository.save(userMapper.convertToEntity(userRequest));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario cadastrado no sistema");
    }

    private void validateTransaction(User userTransfering, TransferDTO transferDTO){
        if (userTransfering.getUserType() == UserType.LOJISTA){
            throw new UserNotAllowedException();
        }
        else if (userTransfering.getSaldo().compareTo(transferDTO.value()) < 0){
            throw new InsuficentBalanceHandler();
        }
        if (!authorizationService.isAuthorized()){
            throw new RuntimeException("Transferencia não autorizada");
        }
    }
}
