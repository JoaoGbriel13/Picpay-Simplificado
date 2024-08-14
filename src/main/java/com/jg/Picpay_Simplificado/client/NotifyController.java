package com.jg.Picpay_Simplificado.client;

import com.jg.Picpay_Simplificado.client.dto.NotifyResponse;
import com.jg.Picpay_Simplificado.model.Transfer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "Notify", url = "https://util.devi.tools/api/v1/notify")
public interface NotifyController {
    @PostMapping
    ResponseEntity<NotifyResponse> notify(Transfer transfer);
}
