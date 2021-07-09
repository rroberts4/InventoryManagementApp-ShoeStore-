///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            LoginSceneController.java
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;


public class LoginSceneController {

    @FXML
    private Button loginButton, exitButton, registerButton;;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    private static int employeeIdNumber;





    public void exitButtonAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        //Query return count if user entered username and password match in the inventory Database
        String verifyLogin = "SELECT count(1),employeeID FROM employee WHERE username = '" + usernameTextField.getText() +"' AND pin ='" + passwordTextField.getText()  +"'";

        try{

            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);



            while(queryResult.next()){

                //if there is amatch set Employee Id and load employee home screen
                if(queryResult.getInt("count(1)") == 1){
                    setEmployeeId(queryResult.getInt("employeeID"));

                    loadHomeScreen();

                }else{
                    messageLabel.setText("Invalid Login. Please Try Again.");
                }

            }

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    public void loginButtonAction(ActionEvent event) throws Exception {

        if(checkIfEmployeeLoginIsEmpty()){
           validateLogin();
       }
    }

    public boolean checkIfEmployeeLoginIsEmpty(){
        if(!usernameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()){
            return true;

        } else{
            messageLabel.setText("Please enter username and pasword");
            return false;
        }
    }
    public void loadHomeScreen() throws Exception {

        //send User to Employee Home Screen
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreenScene.fxml"));
        Stage window =(Stage) loginButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));


    }

    public void registerButtonAction(ActionEvent actionEvent)throws Exception {

        //send User to Employee Registration Screen
        Parent root = FXMLLoader.load(getClass().getResource("RegisterScene.fxml"));
        Stage window =(Stage) registerButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));
    }

    public void setEmployeeId(int employeeId){
        employeeIdNumber = employeeId;

    }
    public static int getEmployeeId(){
       return employeeIdNumber;

    }

}
