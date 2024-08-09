package com.jg.Picpay_Simplificado.dto;

import com.jg.Picpay_Simplificado.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest{
    private Long id;
    private String name;
    private String email;
    private String document;
    private UserType userType;
    private String password;
    private Double saldo;
}
