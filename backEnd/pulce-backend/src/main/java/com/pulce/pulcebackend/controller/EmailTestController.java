package com.pulce.pulcebackend.controller;

import com.pulce.pulcebackend.service.NotificationScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ONLY TEST THEN DELETE

@RestController
@RequestMapping("/api/test")
public class EmailTestController {

    @Autowired
    private NotificationScheduler notificationScheduler;

    @GetMapping("/send-now")
    public String triggerEmail() {
        notificationScheduler.sendExpirationReminder();
        return "Email task triggered manually!";
    }
}
