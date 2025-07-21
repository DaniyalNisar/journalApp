package com.daniyal.journalApp.repository;

import com.daniyal.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,String> {

    Optional<JournalEntry> findById(ObjectId id);
    void deleteById(ObjectId id);

}
