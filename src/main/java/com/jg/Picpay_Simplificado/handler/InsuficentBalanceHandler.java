package com.jg.Picpay_Simplificado.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@EqualsAndHashCode(callSuper = true)
@Data
public class InsuficentBalanceHandler extends RuntimeException{
    public ProblemDetail toProblemDetail(){
        var pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setTitle("Saldo insuficiente");
        return pd;
    }
}
