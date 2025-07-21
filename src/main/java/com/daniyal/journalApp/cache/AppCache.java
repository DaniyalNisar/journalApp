package com.daniyal.journalApp.cache;

import com.daniyal.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> appCache;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        configJournalAppRepository.findAll().forEach(configJournalApp -> {
            appCache.put(configJournalApp.getKey(), configJournalApp.getValue());
        });
    }
}
