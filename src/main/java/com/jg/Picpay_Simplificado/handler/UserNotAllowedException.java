package com.jg.Picpay_Simplificado.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserNotAllowedException extends PicPayHandler{
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.METHOD_NOT_ALLOWED);
        pb.setTitle("Usuario não autorizado");
        pb.setDetail("Lojistas não podem transferir dinheiro");
        return pb;
    }
}
