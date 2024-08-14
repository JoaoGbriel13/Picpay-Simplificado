package com.jg.Picpay_Simplificado.mapper;

import com.jg.Picpay_Simplificado.dto.TransferDTO;
import com.jg.Picpay_Simplificado.dto.UserDTO;
import com.jg.Picpay_Simplificado.model.Transfer;
import com.jg.Picpay_Simplificado.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Transfer convertToEntity(TransferDTO transferDTO) {
        return modelMapper.map(transferDTO, Transfer.class);
    }

    public TransferDTO convertToDto(Transfer transfer) {
        return modelMapper.map(transfer, TransferDTO.class);
    }
}
