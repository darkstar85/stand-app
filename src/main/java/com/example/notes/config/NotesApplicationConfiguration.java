package com.example.notes.config;

import com.yammer.dropwizard.config.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class NotesApplicationConfiguration extends Configuration {

    @NotNull
    private String message;

    @Valid
    private MongoDBConfiguration mongoDBConfiguration;

    public String getMessage() {
        return message;
    }

    public MongoDBConfiguration getMongoDBConfiguration() {
        return mongoDBConfiguration;
    }
}
