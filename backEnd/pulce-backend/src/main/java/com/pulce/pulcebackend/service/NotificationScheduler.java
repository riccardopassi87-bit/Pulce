package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationScheduler {

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
            message.setTo("riccardo.passi87@gmail.com");
            message.setFrom("bonzos.og@gmail.com");
            message.setSubject(product + ": is going to expire in " + days + " days");
            message.setText(product +"\nis going to expire in " + days + " days! \non: " + expiration);

        mailSender.send(message);
        }
    }
}
