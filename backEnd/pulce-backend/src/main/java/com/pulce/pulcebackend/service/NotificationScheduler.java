package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
    public void scheduleCheck(){
        performCheck();
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onStartup(){
        performCheck();
    }

    private void performCheck(){
        int[] threshold = {7, 20, 30};

        for (int days : threshold){
            List<Item> needingNotification = itemRepository.findItemsNeedingNotification(days);

            if(!needingNotification.isEmpty()){
                sendEmail(needingNotification, days);

                for(Item item: needingNotification){
                    item.setLastNotificationSent(days);
                    itemRepository.save(item);
                }
            }
        }
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
