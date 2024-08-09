package com.jg.Picpay_Simplificado.dto;

public record TransferRequest(
        double value,
        long payer,
        long payee
) {
}
