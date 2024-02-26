module com.example.project2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;


    opens com.example.project2 to javafx.fxml;
    exports com.example.project2;
}