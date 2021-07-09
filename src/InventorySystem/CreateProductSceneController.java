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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Connection;
import java.util.ResourceBundle;

import static java.lang.Character.isDigit;

public class CreateProductSceneController implements Initializable {

    @FXML
    private TextField upcTextfield, nameTextfield, priceTextfield, countTextfield;

    @FXML
    private Button exitButton, addItemButton;
    @FXML
    private Label productMessageLabel, newLabel;
    @FXML
    private ComboBox<String> colorComboBox, brandComboBox, typeComboBox, sizeComboBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setProductComboBoxes();
    }

public void setProductComboBoxes(){

    ObservableList<String> colorList = FXCollections.observableArrayList("BLACK", "BLUE", "BROWN", "GREEN", "GREY", "ORANGE", "PINK", "PURPLE", "WHITE", "YELLOW");
    ObservableList<String> brandList = FXCollections.observableArrayList("Adidas", "Converse", "New Balance", "Nike", "Puma", "Reebok", "Sketchers", "UnderArmor");
    ObservableList<String> typeList = FXCollections.observableArrayList("SHOE", "SOCK");
    ObservableList<String> sizeList = FXCollections.observableArrayList("8", "9", "10", "11", "12", "13", "14", "15");
    colorComboBox.setItems(colorList);
    brandComboBox.setItems(brandList);
    typeComboBox.setItems(typeList);
    sizeComboBox.setItems(sizeList);

}

    public void exitButtonAction(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("InventoryScene.fxml"));
        Stage window = (Stage) exitButton.getScene().getWindow();
        window.setScene(new Scene(root, 800, 700));


    }
    public boolean verifyPrice() {

        String price = priceTextfield.getText();

        if (price.length() < 4 || price.length() < 6) {
            productMessageLabel.setText("Price Range must fall between ($0.01 -$999.99)");
            return false;


        }
        int decimalPoint = price.length() - 3;

        for(int i =0;  i < price.length(); i++){
           if((i == decimalPoint && price.charAt(i) != '.') ){
               productMessageLabel.setText("Price must be a numerical value with proper decimal placement");
               return false;

           } else if(isDigit(price.charAt(i)) || i == decimalPoint ){

               //do nothing

            } else{
               String stuff = " " + price.charAt(i);
               newLabel.setText(stuff);
               productMessageLabel.setText("Price must be a numerical value");
                return false;
            }

        }
        return true;
    }



    public boolean verifyCount() {
        if (countTextfield.getText().matches("\\d*") && countTextfield.getText().length() < 4) {

            return true;
        } else {
            productMessageLabel.setText("Inventory count can't exceed 999 and must be a numeric value");
            return false;
        }

    }





    public boolean verifyUpc() {

        productMessageLabel.setText("");

        if (upcTextfield.getText().length() == 7 && upcTextfield.getText().matches("\\d+")) {

            productMessageLabel.setText("");

        } else {
            productMessageLabel.setText("Product UPC must have a value of 7 digits");

            return false;
        }

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connect = connectNow.getConnection();

        //Query reuturns count if Upc exists in database
        String verifyLogin = "SELECT count(1) FROM product WHERE upc = '" + upcTextfield.getText() + "';";

        try {

            Statement statement = connect.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    productMessageLabel.setText("Product Upc entered already exists");
                    return false;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return true;
    }

    public void addItemButtonAction(ActionEvent event) throws Exception {
        if (colorComboBox.getSelectionModel().isEmpty() || brandComboBox.getSelectionModel().isEmpty() || typeComboBox.getSelectionModel().isEmpty()) {
            productMessageLabel.setText("Please make Product Brand, Type and Color Selections");
            return;
        }

        if (verifyUpc() && verifyCount() && verifyPrice()) {
            createNewProduct();
        } else {

            return;
        }


    }

    public void createNewProduct() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connect = connectNow.getConnection();

        String productUpc = upcTextfield.getText();
        String productType = typeComboBox.getSelectionModel().getSelectedItem();
        String productBrand = brandComboBox.getSelectionModel().getSelectedItem();
        String productColor = colorComboBox.getSelectionModel().getSelectedItem();
        String productSize = sizeComboBox.getSelectionModel().getSelectedItem();
        String productPrice = priceTextfield.getText();
        String productName = nameTextfield.getText();
        String productCount = countTextfield.getText();
        newLabel.setText(sizeComboBox.getSelectionModel().getSelectedItem());

        String insertField = "INSERT INTO product (upc,price,brand,name,color,size,type,inventoryCount) Values('";

        String insertValues = productUpc + "','" + productPrice + "','" + productBrand + "','" + productName + "','" + productColor + "','" + productSize + "','" + productType + "','" + productCount + "');";

        String productInsertedValues = insertField + insertValues;

        try {
            Statement statement = connect.createStatement();
            statement.executeUpdate(productInsertedValues);
            productMessageLabel.setText("New Inventory Item Created");
            Parent root = FXMLLoader.load(getClass().getResource("InventoryScene.fxml"));
            Stage window = (Stage) addItemButton.getScene().getWindow();
            window.setScene(new Scene(root, 800, 700));


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }

    }


}

