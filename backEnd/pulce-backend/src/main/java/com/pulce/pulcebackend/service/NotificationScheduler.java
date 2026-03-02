package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.repository.ItemRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        try {
            for (Item item : expiringItems) {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setTo(adminEmail);
                helper.setFrom(senderEmail);

                String subjectEmoji = (days <= 7) ? "🚨 URGENT: " : "📅 REMINDER: ";
                helper.setSubject(subjectEmoji + item.getName() + " expires in " + days + " days");

                String htmlContent = String.format(
                        "<div style='font-family: Arial, sans-serif; font-size: 18px; color: #333;'>" +
                                "The product <b style='color: red; font-size: 22px;'>%s</b> " +
                                "is going to expire in <b style='color: red; font-size: 22px;'>%d</b> days!" +
                                "<br><br>" +
                                "Expiration Date: <strong>%s</strong>" +
                                "</div>",
                        item.getName(),
                        days,
                        item.getExpirationDate()
                );
                helper.setText(htmlContent, true);

                mailSender.send(message);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
