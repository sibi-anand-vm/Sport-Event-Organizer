package com.example.project2;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.codecs.configuration.CodecRegistry;

import java.io.IOException;

public class SEOApplication extends Application {

    private static MongoClient mongoClient;
    private static MongoCollection<Document> userDetailsCollection;
    public static boolean performlogin(String usermail, String password) {
        try {
            // Query the database to find a user with the given usermail and password
            Document query = new Document("usermail", usermail).append("password", password);
            Document user = userDetailsCollection.find(query).first();

            // If a user with the given credentials is found, store the username and return true
            if (user != null) {
                String username = user.getString("username");
                System.out.println("Login successful! Welcome, " + username + ".");
                UserContext.getInstance().setCurrentUsername(username);
                // Now, 'username' contains the username, and you can use it as needed.
                return true;
            } else {
                System.out.println("Invalid credentials. Login failed.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error validating user in MongoDB: " + e.getMessage());
            return false; // Return false in case of an error
        }
    }

    public static void addevent(String ename, String eid, String sname, String place, String time) {
        try {
            // Create a document with event details
            Document eventDocument = new Document()
                    .append("ename", ename)
                    .append("eid", eid)
                    .append("sname", sname)
                    .append("place", place)
                    .append("time", time);
String username=UserContext.getInstance().getCurrentUsername();
            // Create the collection name based on the username
            String userEventsCollectionName = username + "events";

            // Insert the document into the user's events collection
            mongoClient.getDatabase("practice01")
                    .getCollection(userEventsCollectionName)
                    .insertOne(eventDocument);

            System.out.println("Event added successfully for user: " + username);
        } catch (Exception e) {
            System.err.println("Error adding event to MongoDB: " + e.getMessage());
        }
    }
    public static void deleteevent(String eventName, String sportName) {
        try {
            String username = UserContext.getInstance().getCurrentUsername();
            String userEventsCollectionName = username + "events";

            // Define the query to find the event by name and sport
            Document query = new Document("ename", eventName)
                    .append("sname", sportName);

            System.out.println("Query: " + query);

            // Delete the document from the user's events collection
            DeleteResult result = mongoClient.getDatabase("practice01")
                    .getCollection(userEventsCollectionName)
                    .deleteOne(query);

            System.out.println("Deleted Count: " + result.getDeletedCount());

            if (result.getDeletedCount() > 0) {
                System.out.println("Event deleted successfully for user: " + username);
            } else {
                System.out.println("Event not found or not deleted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error deleting event from MongoDB: " + e.getMessage());
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SEOApplication.class.getResource("signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 580);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // Initialize MongoDB connection once the application starts
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("practice01");
            userDetailsCollection = database.getCollection("userdetails");
        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
        }
    }
    public static void performsignup(String username, String usermail, String password) {
        try {
            // Create a document with user details
            Document userDocument = new Document()
                    .append("username", username)
                    .append("usermail", usermail)
                    .append("password", password);

            // Insert the document into the MongoDB collection
            userDetailsCollection.insertOne(userDocument);

            System.out.println("User registered successfully!");

            // Create a collection named "${username}events"
            String userEventsCollectionName = username + "events";

            // Use the codec registry from MongoClientSettings
            CodecRegistry codecRegistry = MongoClientSettings.getDefaultCodecRegistry();

            // Create the new collection with the specified codec registry
            mongoClient.getDatabase("practice01")
                    .withCodecRegistry(codecRegistry)
                    .createCollection(userEventsCollectionName);

            System.out.println("User events collection created: " + userEventsCollectionName);

        } catch (Exception e) {
            System.err.println("Error inserting user into MongoDB: " + e.getMessage());
        }
    }
    @Override
    public void stop() {

        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
