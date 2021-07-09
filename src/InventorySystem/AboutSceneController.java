///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            AboutScene.java
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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutSceneController implements Initializable {

    @FXML
    private Button returnButton;
    @FXML
    private Label aboutMessageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayAboutMessage();

    }

    public void returnButtonAction() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreenScene.fxml"));
        Stage window =(Stage) returnButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));
    }
    public void displayAboutMessage(){

        aboutMessageLabel.setText("///////////////////////////////////////////////////////////////////////////////\n" +
                "//\n" +
                "// Title:           InventoryManagementDB\n" +
                "// Date:            June 2021\n" +
                "//\n" +
                "// Author:          Ryan Jordan Roberts\n" +
                "/*\n" +
                " * This Application manages inventory for a shoe store company.\n" +
                " * Shoe store employees are able to Login and see list of\n" +
                " * --customers\n" +
                " * --employees\n" +
                " * --customer orders\n" +
                " * --shoe manufacturers\n" +
                " * --manufacturers orders\n" +
                " * Users will be able to search through these table list and create new orders and shoe products.\n" +
                " * All table lists and employee login info are stored using a MYSQL Database.\n" +
                " */\n" +
                "///////////////////////////////////////////////////////////////////////////////");
    }
}
