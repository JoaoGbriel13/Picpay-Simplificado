package com.jg.Picpay_Simplificado.service.impl;

import com.jg.Picpay_Simplificado.client.AuthorizationController;
import com.jg.Picpay_Simplificado.handler.PicPayHandler;
import com.jg.Picpay_Simplificado.service.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceimpl implements AuthorizationService {
    private final AuthorizationController authorizationController;

    public AuthorizationServiceimpl(AuthorizationController authorizationController) {
        this.authorizationController = authorizationController;
    }

    @Override
    public boolean isAuthorized(){
        var response = authorizationController.isAuthorized();
        if (response.getStatusCode().isError()) {
            throw new PicPayHandler();
        }
        return response.getBody().authorized();
    }
}
