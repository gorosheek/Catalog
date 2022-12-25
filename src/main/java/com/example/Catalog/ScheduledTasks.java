package com.example.Catalog;

import com.example.Catalog.Repositories.UserRepository;
import com.example.Catalog.Repositories.WarrantyCardRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduledTasks {
    private final UserRepository userRepository;
    private final WarrantyCardRepository warrantyCardRepository;
    private final JavaMailSender javaMailSender;

    public ScheduledTasks(UserRepository userRepository, WarrantyCardRepository warrantyCardRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.warrantyCardRepository = warrantyCardRepository;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(fixedRate = 86400000)
     public void reportCurrentTime() {
        var cards = warrantyCardRepository.findAll();
        for (var card : cards) {
            var currentDate = new Date().getTime();
            var dateOfEnd = card.getEndDate().getTime();
            var diff = currentDate - dateOfEnd;
            var timeUnit = Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            if (diff < 0 && (timeUnit == 30 || timeUnit == 31)) {
                var user = userRepository.findById(card.getUser().getId());
                if (user.isEmpty()) continue;
                var email = user.get().getEmail();

                var simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(email);
                simpleMailMessage.setSubject("Warranty card ended");
                simpleMailMessage.setText("Your warranty card for " + card.getTechnicName() + " ended after a month");
                    javaMailSender.send(simpleMailMessage);
            }
        }
     }
}
