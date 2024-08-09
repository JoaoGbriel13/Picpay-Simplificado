package com.jg.Picpay_Simplificado.handler;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ControllerErrorHandler {
    @ExceptionHandler(PicPayHandler.class)
    public ProblemDetail PicPayException(PicPayHandler picPayHandler){
        return picPayHandler.toProblemDetail();
    }
    @ExceptionHandler(InsuficentBalanceHandler.class)
    public ProblemDetail insuficienteBalance(InsuficentBalanceHandler in){
        return in.toProblemDetail();
    }

}
