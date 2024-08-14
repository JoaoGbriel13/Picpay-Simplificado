package com.jg.Picpay_Simplificado.mapper;

import com.jg.Picpay_Simplificado.dto.UserDTO;
import com.jg.Picpay_Simplificado.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User convertToEntity(UserDTO userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
