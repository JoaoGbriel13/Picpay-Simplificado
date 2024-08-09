package com.jg.Picpay_Simplificado.handler;

import feign.FeignException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@EqualsAndHashCode(callSuper = true)
@Data
public class PicPayHandler extends RuntimeException {
    public ProblemDetail toProblemDetail(){
        var pb  = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        pb.setTitle("Transação não autorizada");
        pb.setDetail("Banco não autorizou a transação, tente novamente mais tarde");
        return pb;
    }
}
