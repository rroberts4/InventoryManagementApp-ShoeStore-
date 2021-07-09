///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            CustomerSceneController.java
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerSceneController implements Initializable {

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer,String>  columnId;
    @FXML
    private TableColumn<Customer,String>  columnName;
    @FXML
    private TableColumn<Customer,String>  columnAddress;
    @FXML
    private TableColumn<Customer,String>  columnDate;
    @FXML
    private TableColumn<Customer,String>  columnPhone;

    @FXML
    private TextField filterField;
    @FXML
    private Button exitButton;

    ObservableList<Customer> list = FXCollections.observableArrayList();
    FilteredList<Customer> filterList = new FilteredList<>(list,b-> true);






    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillCustomerTable();
        createFilteredListForCustomerTable();

    }


    public void fillCustomerTable(){

        try {

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection con = connectNow.getConnection();
            ResultSet result = con.createStatement().executeQuery("SELECT name, customer.idCustomer, address, phone, date FROM customerorder, customer group by customer.idCustomer;");

            while(result.next()){
                list.add(new Customer(result.getString("name"),
                        result.getString("idCustomer"), result.getString("address"), result.getString("phone"),
                        result.getString("date")));
            }
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
            Logger.getLogger(CustomerSceneController.class.getName()).log(Level.SEVERE,null,e);

        }


        columnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("customerDate"));


        customerTable.setItems(list);



    }
    public void createFilteredListForCustomerTable(){

        //Wrap ObservableList in a FilteredList

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterList.setPredicate(Customer -> {

                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                //Compare user textfield with the names and roles of each employee
                String lowerCaseValue = newValue.toLowerCase();

                if(Customer.getCustomerName().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Customer.getCustomerAddress().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Customer.getCustomerId().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Customer.getCustomerDate().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                } else if(Customer.getCustomerPhone().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else{
                    return false;
                }

            });
        });

        SortedList<Customer> sortedList = new SortedList<>(filterList);

        sortedList.comparatorProperty().bind(customerTable.comparatorProperty());

        customerTable.setItems(sortedList);

    }
    public void exitButtonPressed(ActionEvent actionEvent)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreenScene.fxml"));
        Stage window =(Stage) exitButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));
    }
}



