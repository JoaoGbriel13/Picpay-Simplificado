package com.jg.Picpay_Simplificado.dto;

import java.math.BigDecimal;

public record TransferDTO(
        BigDecimal value,
        long payer,
        long payee
) {
}
