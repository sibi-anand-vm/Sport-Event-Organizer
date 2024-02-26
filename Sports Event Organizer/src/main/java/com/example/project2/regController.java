package com.example.project2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class regController {
    public Button loginButton;
    public Label welcomeText;
    public TextField eventnameTextField;
    public TextField eventidtypeTextField;
    public TextField sportsnameTextField;
    public TextField placeTextField;
    public TextField starttimeTextField;
    public Button confirmButton;

    public void onmenuButtonClick(ActionEvent actionEvent) {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = loader.load();

            // Create a new Scene
            Scene loginScene = new Scene(root);

            // Get the current stage (assuming your signup.fxml is in the same stage)
            Stage stage = (Stage) welcomeText.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(loginScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onconfirmButtonclick(ActionEvent actionEvent) {
        try {
            String ename = eventnameTextField.getText();
            String  eid = eventidtypeTextField.getText();
            String sname = sportsnameTextField.getText();
            String place = placeTextField.getText();
            String time=starttimeTextField.getText();

            // Check if any of the fields are empty
            if (ename.isEmpty() || eid.isEmpty() || sname.isEmpty() || place.isEmpty() || time.isEmpty()) {
                System.out.println("Please fill in all fields.");
                return;
            }
            SEOApplication.addevent(ename,eid,sname,place,time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
