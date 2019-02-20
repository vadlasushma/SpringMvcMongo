package com.test1.pluto1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import  com.test1.pluto1.factory.MongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.springframework.web.bind.annotation.RequestMapping;

@Service("userService")
@Transactional
public class UserService {

    static String db_name = "mydb", db_collection = "mycollection";
    private static Logger log = LoggerFactory.getLogger(UserService.class);

    // Fetch all users from the mongo database.
    //@RequestMapping("/")
    public List getAll() {
        List user_list = new ArrayList();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

        // Fetching cursor object for iterating on the database records.
        DBCursor cursor = coll.find();
        while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();

            User user = new User();
            Record(user, dbObject);


            //Adding the user details to the list.
            boolean add = user_list.add(user);
        }
       // log.debug("Total records fetched from the mongo database are= " + user_list.size());
        return user_list;
    }
    // Fetching a single user details from the mongo database.
    public User findUserId(String id) {
        User u = new User();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject();
        where_query.put("id", id);

        DBObject dbo = coll.findOne(where_query);
        Record(u, dbo);

        //Return user object.
        return u;
    }//method
    private void Record(User u, DBObject dbo) {
        u.setId(dbo.get("id").toString());
        u.setName(dbo.get("name").toString());
        u.setLanguage(dbo.get("language").toString());
        u.setCity(dbo.get("city").toString());
        u.setDescription(dbo.get("description").toString());
        u.setEvent_type(dbo.get("event_type").toString());
    }
   // @RequestMapping(value = "/")
    // Add a new user to the mongo database.
    public Boolean add(User user) {
        boolean output=false;
        Random ran = new Random();
       // log.debug("Adding a new user to the mongo database; Entered user_name is= " + user.getName());
        try {
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

            // Create a new object and add the new user details to this object.
            {
                BasicDBObject doc = new BasicDBObject();
                doc.put("id", String.valueOf(ran.nextInt(100)));
                Edit(user, doc);


                // Save a new user to the mongo collection.
            coll.insert(doc);}
            output = true;
        } catch (Exception e) {
            output = false;
            //log.error("An error occurred while saving a new user to the mongo database", e);
        }
        return output;
    }

    // Update the selected user in the mongo database.
    public Boolean edit(User user) {
       // log.debug("Updating the existing user in the mongo database; Entered user_id is= " + user.getId());
        boolean output =false;
        try {
            // Fetching the user details.
            BasicDBObject existing = (BasicDBObject) getDBObject(user.getId());

            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

            { // Create a new object and assign the updated details.
             BasicDBObject edited = new BasicDBObject();
                 edited.put("id", user.getId());
                Edit(user, edited);


                // Update the existing user to the mongo database.
            coll.update(existing, edited);}
            output = true;
        } catch (Exception e) {
            output = false;
            log.error("An error has occurred while updating an existing user to the mongo database", e);
        }
        return output;
    }
  //method for adding user
    private void Edit(User user, BasicDBObject edited) {
        edited.put("name",user.getName());
        edited.put("language",user.getLanguage());
        edited.put("city", user.getCity());
        edited.put("description", user.getDescription());
        edited.put("event_type", user.getEvent_type());
    }

    // Delete a user from the mongo database.
    public void delete(String id) {
        boolean output = false;
        log.debug("Deleting an existing user from the mongo database; Entered user_id is= " + id);
        try {
            // Fetching the required user from the mongo database.
            BasicDBObject item = (BasicDBObject) getDBObject(id);

            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

            // Deleting the selected user from the mongo database.
            coll.remove(item);
            output = true;
        } catch (Exception e) {
            log.error("An error occurred while deleting an existing user from the mongo database", e);
        }
    }

    // Fetching a particular record from the mongo database.
    private DBObject getDBObject(String id) {
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject();

        // Put the selected user_id to search.
        where_query.put("id", id);
        return coll.findOne(where_query);
    }




}
