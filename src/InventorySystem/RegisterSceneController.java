///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            RegisterSceneController.java
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.sql.Statement;
import java.sql.Connection;




public class RegisterSceneController {

    @FXML
    private TextField firstnameTextfield, lastnameTextfield, emailTextfield, usernameTextfield;
    @FXML
    private PasswordField passwordField, confirmPasswordField;
    @FXML
    private Button exitButton, registerButton;
    @FXML
    private Label registerMessageLabel, passwordErrorLabel;



    public void exitButtonAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        Stage window =(Stage) exitButton.getScene().getWindow();
        window.setScene(new Scene(root, 520,400));


    }

    public void registerButtonAction(ActionEvent event) throws Exception {

        if(checkIfEnteredPasswordMatch()){
            registerEmployee();
        }






    }

    public boolean checkIfEnteredPasswordMatch(){

        if(passwordField.getText().equals(confirmPasswordField.getText())) {
            return true;
        } else {
            passwordErrorLabel.setText("Password does not match!");
            return false;
        }
    }

    public void registerEmployee(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connect = connectNow.getConnection();

        String firstname = firstnameTextfield.getText();
        String lastname = lastnameTextfield.getText();
        String username = usernameTextfield.getText();
        String password = passwordField.getText();
        String email = emailTextfield.getText();


        //Query creates new Employee in SQL Database

        String insertField = "INSERT INTO employee (firstname,lastname,email,username,pin, workshift,jobcode) Values('";
        String insertValues = firstname + "','" + lastname + "','" + email + "','" + username + "','" + password + "','ON','Stocker');";
        String employeeInsertedValues = insertField + insertValues;

        try{

            Statement statement = connect.createStatement();
            statement.executeUpdate(employeeInsertedValues);

            registerMessageLabel.setText("Employee Registration Complete");

            //Send User back to theLogin Screen
            Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
            Stage window =(Stage) registerButton.getScene().getWindow();
            window.setScene(new Scene(root, 520,400));


        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}

