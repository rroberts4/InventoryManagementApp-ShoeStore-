///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            ManufacturerSceneController.java
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


public class ManufacturerSceneController implements Initializable {

    @FXML
    private TableView<Manufacturer> manufacturerTable;
    @FXML
    private TableColumn<Manufacturer,String>  columnId;
    @FXML
    private TableColumn<Manufacturer,String>  columnName;
    @FXML
    private TableColumn<Manufacturer,String>  columnDate;
    @FXML
    private TableColumn<Manufacturer,String>  columnManufacturer;
    @FXML
    private TableColumn<Manufacturer,String>  columnQuantity;
    @FXML
    private TableColumn<Manufacturer,String>  columnUpc;


    @FXML
    private TextField filterField;
    @FXML
    private Button exitButton;

    ObservableList<Manufacturer> list = FXCollections.observableArrayList();
    FilteredList<Manufacturer> filterList = new FilteredList<>(list,b-> true);




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            fillManufacturersTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createFilterSearchForManufacturersTable();







    }
    public void fillManufacturersTable() throws Exception{
        try {

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection con = connectNow.getConnection();

            ResultSet result = con.createStatement().executeQuery("SELECT  date, manufacturer.name, inventoryshipment.manufacturer_id,product.name, inventoryshipment.upc,quantity FROM inventoryshipment\n" +
                    "    inner join manufacturer on manufacturer.manufacturer_id=inventoryshipment.manufacturer_id\n" +
                    "    inner join product on inventoryshipment.upc =product.upc order BY date, manufacturer.name;");

            while(result.next()){
                list.add(new Manufacturer(result.getString("date"),
                        result.getString("name"), result.getString("manufacturer_id"), result.getString("product.name"),
                        result.getString("inventoryshipment.upc"),result.getString("quantity")));
            }
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
            Logger.getLogger(ManufacturerSceneController.class.getName()).log(Level.SEVERE,null,e);

        }



        columnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("manufacturerId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("manufacturerProduct"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("manufacturerDate"));
        columnUpc.setCellValueFactory(new PropertyValueFactory<>("productUpc"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("manufacturerQuantity"));


        manufacturerTable.setItems(list);

    }

    public void createFilterSearchForManufacturersTable(){

        //Wrap ObservableList in a FilteredList

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterList.setPredicate(Manufacturer -> {

                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                //Compare User's input with the Manufactures Name Id etc

                String lowerCaseValue = newValue.toLowerCase();



                if(Manufacturer.getManufacturerDate().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Manufacturer.getManufacturerId().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Manufacturer.getManufacturerName().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Manufacturer.getManufacturerProduct().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                } else if(Manufacturer.getProductUpc().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }

                else{
                    return false;
                }

            });
        });

        SortedList<Manufacturer> sortedList = new SortedList<>(filterList);

        sortedList.comparatorProperty().bind(manufacturerTable.comparatorProperty());

        manufacturerTable.setItems(sortedList);

    }

    public void exitButtonPressed(ActionEvent actionEvent)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreenScene.fxml"));
        Stage window =(Stage) exitButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));
    }
}



