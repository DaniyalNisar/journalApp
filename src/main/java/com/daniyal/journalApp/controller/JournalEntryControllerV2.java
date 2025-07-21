package com.daniyal.journalApp.controller;

import com.daniyal.journalApp.entity.JournalEntry;
import com.daniyal.journalApp.entity.User;
import com.daniyal.journalApp.service.JournalEntryService;
import com.daniyal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Controllers are special types of components that handle http requests
@RestController
@RequestMapping("/journal")  //adds mapping to entire class
public class JournalEntryControllerV2 {
    //we'll right specific endpoints here

    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public ResponseEntity<?> createJournalEntry(@RequestBody JournalEntry journalEntry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        try {
            journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getJournalEntriesofUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUsername(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if (!journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntrybyId(@PathVariable ObjectId myId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUsername(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(journalEntry -> journalEntry.getId().equals(myId)).toList();

        //if you want mutable list use collect(Collectors.toList()), jnstead of just tolist()
        if (!journalEntries.isEmpty()) {
            Optional<JournalEntry> jE = journalEntryService.getJournalEntryById(myId);
            if (jE.isPresent()) {
                return new ResponseEntity<>(jE.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //cascade delte not supported in mongo db , need to do it manually
    //casecade deletem means e.g deleting journaly entry in our case will not automatically delete it reference
    //from the user
    //However when calling next save journal entry for the same user issue is resolved. conisstency is achieved, because spring doesn't fetch the non-existenmt journal entry
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        boolean removed = journalEntryService.deleteJournalEntryById(myId, userName);
        if (removed) {
            return new ResponseEntity<>("Deleted journal entry by id: " + myId, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Journal entry no found against Id: " + myId, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry journalEntry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUsername(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(journalE -> journalE.getId().equals(myId)).toList();
        if (!journalEntries.isEmpty()) {
            JournalEntry oldJournalEntry = journalEntryService.getJournalEntryById(myId).orElse(null);
            if (oldJournalEntry != null) {
                oldJournalEntry.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : oldJournalEntry.getTitle());
                oldJournalEntry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : oldJournalEntry.getContent());
                journalEntryService.saveEntry(oldJournalEntry);
                return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
