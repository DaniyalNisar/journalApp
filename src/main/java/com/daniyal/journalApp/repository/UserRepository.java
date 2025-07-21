package com.daniyal.journalApp.repository;

import com.daniyal.journalApp.entity.JournalEntry;
import com.daniyal.journalApp.entity.User;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {


    Optional<User> findById(ObjectId id);
    void deleteById(ObjectId id);
    User findByUsername(String username);

    void deleteByUsername(@NonNull String username);
}
