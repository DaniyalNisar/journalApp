package com.daniyal.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data //THIS ANNOTATION ADDS ARGS CONSTRUCTOR BU FOR DESERIALIZATION WE REQUIRE NOARDS CONSTRUCTOR
@NoArgsConstructor  //REQUIRED WHEN CONVERTIng from JSON TO POJO (DESERIALIZATION)
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull  //lombok will put a null check and through NullPointerException if null
    private String password;

    private List<String> roles;

    private String email;

    private boolean sentimentAnalysis;

    @DBRef //like foreign key
    private List<JournalEntry> journalEntries= new ArrayList<>();

}
