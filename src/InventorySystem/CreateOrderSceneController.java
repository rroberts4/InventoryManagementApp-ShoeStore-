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

import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.Date;
import java.text.SimpleDateFormat;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class CreateOrderSceneController {

    @FXML
    private TextField upcTextfield, quantityTextfield;

    @FXML
    private Button exitButton, orderRequestButton;
    @FXML
    private Label colorPromptLabel, sizePromptLabel, brandPromptLabel, namePromptLabel, quantityPromptLabel;
    @FXML
    private Label productColorLabel, productSizeLabel, productBrandLabel, productNameLabel;
    @FXML
    private Label upcMessageLabel, orderMessageLabel;

    private String manufacturerId, orderQuantity, upcNumber;

    private int employeeId;
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("y-MM-dd");
    String date = dateFormat.format(currentDate);

    public void exitButtonPressed(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("OrderScene.fxml"));
        Stage window = (Stage) exitButton.getScene().getWindow();
        window.setScene(new Scene(root, 800, 700));


    }

    public void searchProductAction(ActionEvent event) {

        if(checkUpcIsCorrectFormat()){
            validateUpc();
        }
    }

    public boolean checkUpcIsCorrectFormat(){
        if( upcTextfield.getText().isEmpty()){
            upcMessageLabel.setText("Please enter a UPC number before searching!");
            upcMessageLabel.setTextFill(Color.web("#e40000"));//red font


            return false;

        }
        else if(upcTextfield.getText().length() == 7 && upcTextfield.getText().matches("\\d+")) {

            return true;

        } else{
            upcMessageLabel.setText("A UPC number must be a 7 digit numeric value");
            return false;

        }
    }





    public void validateUpc() {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        //Query return count if the upc entered is in SQL database
        String verifyLogin = "SELECT count(1) FROM product WHERE upc = '" + upcTextfield.getText() + "'";

        try {

            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    upcMessageLabel.setText("UPC number found continue below to finish order request");

                    displayProductDetails();


                } else {
                    //UPC doesn't exists in SQL database
                    upcMessageLabel.setText("UPC number Invalid, please try again ");
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void displayProductDetails() {

    //displays user to extended options to create a new order
        colorPromptLabel.setVisible(true);
        sizePromptLabel.setVisible(true);
        brandPromptLabel.setVisible(true);
        namePromptLabel.setVisible(true);
        orderRequestButton.setVisible(true);
        quantityPromptLabel.setVisible(true);
        quantityTextfield.setVisible(true);



        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connect = connectNow.getConnection();

        //Query returns product details based on Upc entered by user
        String queryStatement = "SELECT brand, name,color,size FROM product WHERE upc = '" + upcTextfield.getText() + "'";


        try {

            Statement statement = connect.createStatement();
            ResultSet QueryResult = statement.executeQuery(queryStatement);

            while(QueryResult.next()){
                productBrandLabel.setText((QueryResult.getString("brand")));
                productColorLabel.setText((QueryResult.getString("color")));
                productSizeLabel.setText((QueryResult.getString("size")));
                productNameLabel.setText((QueryResult.getString("name")));
            }






        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    public void orderRequestAction(ActionEvent event) throws Exception {
    if(checkQuantityIsCorrectFormat()){
        createNewOrder();
    }

    }

    public boolean checkQuantityIsCorrectFormat(){
        if( quantityTextfield.getText().isEmpty()){
            orderMessageLabel.setText("Please enter an order quantity before attempting an order");
            return false;

        }
        else if(quantityTextfield.getText().length() < 3 && quantityTextfield.getText().matches("\\d+")) {

            return true;

        } else{
            orderMessageLabel.setText("Order quantity must be a numeric value between 1-99");
            return false;

        }
    }

    public String getManufacturerId(String brandName){
        if("Adidas".equals(brandName)){
        return "03987";}

        if (brandName.equals("Converse")) {
            return "02115";
        }
        if("Nike".equals(brandName)){
            return "01223";
        }
        if("Puma".equals(brandName)){
            return "02178";
        }
        if("Reebok".equals(brandName)){
            return "05675";
        }
        if("Sketchers".equals(brandName)){
            return "05543";
        }
        if("New Balance".equals(brandName)){
            return "09543";
        }
        if("UnderArmor".equals(brandName)){
            return "07432";
        }
        if("Vans".equals(brandName)){
            return "02219";
        }
        else{
            //if "00000" Error has occurred
            return "00000";
        }
    }
    public void createNewOrder() {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connect = connectNow.getConnection();

        manufacturerId = getManufacturerId(productBrandLabel.getText());
        employeeId = LoginSceneController.getEmployeeId();
        orderQuantity = quantityTextfield.getText();
        upcNumber = upcTextfield.getText();

        try {

            //Query adds new employee order
            Statement statement = connect.createStatement();
            statement.executeUpdate(("INSERT INTO inventoryorder (employeeID,manufacturer_id,upc,orderDate,orderQuantity) Values('" + employeeId + "','" + manufacturerId + "','" + upcNumber + "','" + date + "','" + orderQuantity+ "');"));
            orderMessageLabel.setText("New Inventory Item Created");

            //send user to a reset of Create Order Scene
            Parent root = FXMLLoader.load(getClass().getResource("createOrderScene.fxml"));
            Stage window = (Stage) orderRequestButton.getScene().getWindow();
            window.setScene(new Scene(root, 800, 700));


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }

    }
}