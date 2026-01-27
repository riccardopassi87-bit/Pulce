package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.repository.ItemRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationScheduler {

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${app.notification.recipient}")
    private String adminEmail;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    public JavaMailSender mailSender;

    @Scheduled(cron = "0 0 10 * * ?")
    @Transactional
    public void sendExpirationReminder() {
        List<Item> itemsExpiringSoon30 = itemRepository.findByExpirationInDays(30);
        List<Item> itemsExpiringSoon20 = itemRepository.findByExpirationInDays(20);
        List<Item> itemsExpiringSoon7 = itemRepository.findByExpirationInDays(7);

        sendEmail(itemsExpiringSoon30, 30);
        sendEmail(itemsExpiringSoon20, 20);
        sendEmail(itemsExpiringSoon7, 7);
    }

    private void sendEmail(List<Item> expiringItems, int days) {

        for (Item item : expiringItems) {
            String product = item.getName();
            String expiration = item.getExpirationDate().toString();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(adminEmail);
            message.setFrom(senderEmail);
            message.setSubject(product + ": is going to expire in " + days + " days");
            message.setText(product +"\nis going to expire in " + days + " days! \non: " + expiration);

        mailSender.send(message);
        }
    }
}
