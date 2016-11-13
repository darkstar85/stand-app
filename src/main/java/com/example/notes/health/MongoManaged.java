package com.example.notes.health;

import com.mongodb.MongoClient;
import com.yammer.dropwizard.lifecycle.Managed;

public class MongoManaged implements Managed {

    private MongoClient mongoClient;

    public MongoManaged(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        mongoClient.close();
    }
}
