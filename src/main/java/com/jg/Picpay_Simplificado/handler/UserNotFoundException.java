package com.jg.Picpay_Simplificado.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserNotFoundException extends PicPayHandler{
    private final long id;

    public UserNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setDetail("Usuario não encontrado: " + id);
        return pb;
    }
}
