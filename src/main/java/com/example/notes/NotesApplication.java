package com.example.notes;

import com.example.notes.config.MongoDBConfiguration;
import com.example.notes.config.NotesApplicationConfiguration;
import com.example.notes.core.service.NoteService;
import com.example.notes.health.MongoHealthCheck;
import com.example.notes.health.MongoManaged;
import com.example.notes.resources.NotesResource;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class NotesApplication extends Service<NotesApplicationConfiguration> {

    private static final Logger log = LoggerFactory.getLogger(NotesApplication.class);

    public static void main(String[] args) throws Exception {
        new NotesApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<NotesApplicationConfiguration> bootstrap) {
        bootstrap.setName("NotesApp");
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(NotesApplicationConfiguration configuration, Environment environment) throws Exception {
        log.info("Starting application - {}", configuration.getMessage());
        DB db = setupMongoDB(configuration.getMongoDBConfiguration(), environment);
        environment.addResource(new NotesResource(new NoteService(db)));
    }

    private DB setupMongoDB(MongoDBConfiguration configuration, Environment environment) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(configuration.getHost(), configuration.getPort());
        DB db = mongoClient.getDB(configuration.getDb());
        MongoManaged mongoManaged = new MongoManaged(mongoClient);
        environment.manage(mongoManaged);
        environment.addHealthCheck(new MongoHealthCheck(mongoClient));
        return db;
    }
}
