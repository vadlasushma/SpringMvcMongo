package com.test1.pluto1.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@SuppressWarnings("deprecation")
public class MongoFactory {

    private static Logger log = LoggerFactory.getLogger(MongoFactory.class);

    private static Mongo mongo;

    private MongoFactory() { }

    // Returns a mongo instance.
    private static Mongo getMongo() {
        int port_no = 27017;
        String hostname = "localhost";
        if (mongo == null) {
            try {
                mongo = new Mongo(hostname, port_no);
            } catch (MongoException ex) {
                log.error(String.valueOf(ex));
            }
        }
        return mongo;
    }

    // Fetches the mongo database.
    private static DB getDB(String db_name) {
        return getMongo().getDB(db_name);
    }

    // Fetches the collection from the mongo database.
    public static DBCollection getCollection(String db_name, String db_collection) {
        return getDB(db_name).getCollection(db_collection);
    }
}



