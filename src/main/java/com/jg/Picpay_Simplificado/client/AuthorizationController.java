package com.jg.Picpay_Simplificado.client;

import com.jg.Picpay_Simplificado.client.dto.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="Auth", url = "https://util.devi.tools/api/v2/authorize")
public interface AuthorizationController {
    @GetMapping
    ResponseEntity<AuthResponse> isAuthorized();
}
