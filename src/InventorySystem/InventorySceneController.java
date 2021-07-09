///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            InventorySceneController.java
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


public class InventorySceneController implements Initializable {

    @FXML
    private TableView<Inventory> inventoryTable;
    @FXML
    private TableColumn<Inventory,String>  columnBrand;
    @FXML
    private TableColumn<Inventory,String>  columnName;
    @FXML
    private TableColumn<Inventory,String>  columnColor;
    @FXML
    private TableColumn<Inventory,String>  columnSize;
    @FXML
    private TableColumn<Inventory,String>  columnCount;
    @FXML
    private TableColumn<Inventory,String>  columnPrice;
    @FXML
    private TableColumn<Inventory,String>  columnUpc;
    @FXML
    private TextField filterField;
    @FXML
    private Button exitButton, createProductButton;

    ObservableList<Inventory> list = FXCollections.observableArrayList();
    FilteredList<Inventory> filterList = new FilteredList<>(list,b-> true);






    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillProductInventoryTable();
        createFilteredListForProductInventoryTable();
    }

    public void fillProductInventoryTable(){

        try {

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection con = connectNow.getConnection();

            //Query returns info on shoe inventory in theDatabase
            ResultSet result = con.createStatement().executeQuery("SELECT brand, name, color, size, inventoryCount, upc, price from product order by brand,name;");

            while(result.next()){
                list.add(new Inventory(result.getString("brand"),
                        result.getString("name"), result.getString("size"), result.getString("color"),
                        result.getString("inventoryCount"), result.getString("upc"),result.getString("price")));
            }
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
            Logger.getLogger(InventorySceneController.class.getName()).log(Level.SEVERE,null,e);

        }

        columnBrand.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        columnColor.setCellValueFactory(new PropertyValueFactory<>("productColor"));
        columnSize.setCellValueFactory(new PropertyValueFactory<>("productSize"));
        columnCount.setCellValueFactory(new PropertyValueFactory<>("productCount"));
        columnUpc.setCellValueFactory(new PropertyValueFactory<>("productUpc"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        inventoryTable.setItems(list);
    }

    public void createFilteredListForProductInventoryTable(){

        //Wrap ObservableList in a FilteredList

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterList.setPredicate(Inventory -> {

                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                //Compare search textField with the info of each product in Inventory
                String lowerCaseValue = newValue.toLowerCase();

                if(Inventory.getProductBrand().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Inventory.getProductName().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Inventory.getProductColor().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Inventory.getProductSize().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                } else if(Inventory.getProductUpc().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Inventory.getProductPrice().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else{
                    return false;
                }

            });
        });

        SortedList<Inventory> sortedList = new SortedList<>(filterList);

        sortedList.comparatorProperty().bind(inventoryTable.comparatorProperty());

        inventoryTable.setItems(sortedList);

    }

    public void createProductAction(ActionEvent actionEvent)throws Exception {
        //sends user to product creation screen
        Parent root = FXMLLoader.load(getClass().getResource("CreateProductScene.fxml"));
        Stage window =(Stage) createProductButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));
    }


    public void exitButtonPressed(ActionEvent actionEvent)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreenScene.fxml"));
        Stage window =(Stage) exitButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));
    }
}



