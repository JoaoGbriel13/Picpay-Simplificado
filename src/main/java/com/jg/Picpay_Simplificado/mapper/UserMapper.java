package com.jg.Picpay_Simplificado.mapper;

import com.jg.Picpay_Simplificado.dto.UserRequest;
import com.jg.Picpay_Simplificado.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User convertToEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public UserRequest convertToDto(User user) {
        return modelMapper.map(user, UserRequest.class);
    }
}
