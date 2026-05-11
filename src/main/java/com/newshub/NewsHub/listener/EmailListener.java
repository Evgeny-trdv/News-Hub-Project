package com.newshub.NewsHub.listener;

import com.newshub.NewsHub.service.impl.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    public final EmailService emailService;

    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = {"email-queue"})
    public void sendEmail(String email) {
        emailService.sendEmail(email, "Register", "Welcome to the board");
    }
}
