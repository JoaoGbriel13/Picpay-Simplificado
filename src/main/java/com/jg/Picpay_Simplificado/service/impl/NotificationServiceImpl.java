package com.jg.Picpay_Simplificado.service.impl;

import com.jg.Picpay_Simplificado.client.NotifyController;
import com.jg.Picpay_Simplificado.model.Transfer;
import com.jg.Picpay_Simplificado.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotifyController notifyController;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationServiceImpl(NotifyController notifyController) {
        this.notifyController = notifyController;
    }


    @Override
    public void sendNotification(Transfer transfer) {
        var response = notifyController.notify(transfer);
        if (response.getStatusCode().isError()){
            logger.error("Erro ao mandar a notificação");
        }
    }
}
