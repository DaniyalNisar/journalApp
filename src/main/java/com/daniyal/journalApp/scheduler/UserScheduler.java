package com.daniyal.journalApp.scheduler;

import com.daniyal.journalApp.entity.JournalEntry;
import com.daniyal.journalApp.entity.User;
import com.daniyal.journalApp.repository.UserRepositoryImpl;
import com.daniyal.journalApp.service.EmailService;
import com.daniyal.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 0 9 * * MON")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUserforSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredJournalEntries = journalEntries.stream().filter(x -> x.getDate().
                            isAfter(LocalDateTime.now().minusDays(7))).
                    map(JournalEntry::getContent).toList();
            String entry = String.join(" ", filteredJournalEntries);
            String sentiment = sentimentAnalysisService.getSentimentAnalysis(entry);
            emailService.sendSimpleMail(user.getEmail(), "Sentiment for Last 7 days", sentiment);
        }
    }

    //appcache can be refresher in the same manner
}
