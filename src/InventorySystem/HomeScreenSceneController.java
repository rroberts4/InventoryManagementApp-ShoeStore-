///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            HomeScreenSceneController.java
// Date:            June 2021
//
// Author:          Ryan Jordan Roberts
/*
 * This Application manages inventory for a shoe store company.
 * Shoe store employees are able to Login and see list of
 * --customers
 * --employees
 * --customer orders
 * --shoe manufacturers
 * --manufacturers orders
 * Users will be able to search through these table list and create new orders and shoe products.
 * All table lists and employee login info are stored using a MYSQL Database.
 */
///////////////////////////////////////////////////////////////////////////////

package InventorySystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HomeScreenSceneController {
    @FXML
    private ImageView staffButton, inventoryButton, customerButton, manufacturerButton, orderButton, aboutButton;
    @FXML
    private Button backButton, exitButton;


    public void backButtonPressed(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        Stage window =(Stage) backButton.getScene().getWindow();
        window.setScene(new Scene(root, 520,400));


    }

    public void staffIconPressed() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("EmployeeScene.fxml"));
        Stage window =(Stage) staffButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));


    }

    public void inventoryIconPressed() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("InventoryScene.fxml"));
        Stage window =(Stage) inventoryButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));


    }

    public void customerIconPressed() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CustomerScene.fxml"));
        Stage window =(Stage) customerButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));


    }

    public void manufacturerIconPressed() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ManufacturerScene.fxml"));
        Stage window =(Stage) manufacturerButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));


    }


    public void orderIconPressed() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("OrderScene.fxml"));
        Stage window =(Stage) orderButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));


    }
    public void aboutButtonPressed() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AboutScene.fxml"));
        Stage window =(Stage) aboutButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));


    }
    public void exitButtonAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
