package com.jg.Picpay_Simplificado;

import com.jg.Picpay_Simplificado.dto.TransferDTO;
import com.jg.Picpay_Simplificado.model.User;
import com.jg.Picpay_Simplificado.model.UserType;
import com.jg.Picpay_Simplificado.repository.TransferRepository;
import com.jg.Picpay_Simplificado.repository.UserRepository;
import com.jg.Picpay_Simplificado.service.AuthorizationService;
import com.jg.Picpay_Simplificado.service.impl.UserServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class PicPayTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private TransferRepository transferRepository;
    @InjectMocks
    private UserServiceimpl userServiceimpl;
    @BeforeEach
    void setUp(){
        openMocks(this);
    }

    @Test
    void authorizedTransaction(){
        User payer = new User(1L, "Payer", "12345678900", "payer@example.com", UserType.USUARIO, "password", BigDecimal.valueOf(100));
        User payee = new User(2L, "Payee", "98765432100", "payee@example.com", UserType.USUARIO, "password", BigDecimal.valueOf(50));

        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, payer.getId(), payee.getId());

        when(userRepository.findById(payer.getId())).thenReturn(Optional.of(payer));
        when(userRepository.findById(payee.getId())).thenReturn(Optional.of(payee));
        when(authorizationService.isAuthorized()).thenReturn(true);

        ResponseEntity response = userServiceimpl.makeTransaction(transferDTO);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(90), payer.getSaldo());
        assertEquals(BigDecimal.valueOf(60), payee.getSaldo());
        System.out.println("Passou!");
    }

    @Test
    void unauthorizedTransaction(){
        User payer = new User(1L, "Payer", "12345678900", "payer@example.com", UserType.USUARIO, "password", BigDecimal.valueOf(100));
        User payee = new User(2L, "Payee", "98765432100", "payee@example.com", UserType.USUARIO, "password", BigDecimal.valueOf(50));

        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, payer.getId(), payee.getId());

        when(userRepository.findById(payer.getId())).thenReturn(Optional.of(payer));
        when(userRepository.findById(payee.getId())).thenReturn(Optional.of(payee));
        when(authorizationService.isAuthorized()).thenReturn(false);

        ResponseEntity response = userServiceimpl.makeTransaction(transferDTO);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(100), payer.getSaldo());
        assertEquals(BigDecimal.valueOf(50), payee.getSaldo());
        System.out.println("Passou!");
    }
}
