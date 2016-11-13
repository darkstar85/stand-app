package com.example.notes.health;

import com.mongodb.MongoClient;
import com.yammer.metrics.core.HealthCheck;

public class MongoHealthCheck extends HealthCheck {

    private MongoClient mongoClient;

    public MongoHealthCheck(MongoClient mongoClient) {
        super("MongoHealthCheck");
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() throws Exception {
        mongoClient.getDatabaseNames();
        return Result.healthy();
    }

}
