///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            OrderScene.java
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


public class OrderSceneController implements Initializable {

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order,String>  columnDate;
    @FXML
    private TableColumn<Order,String>  columnName;
    @FXML
    private TableColumn<Order,String>  columnUpc;
    @FXML
    private TableColumn<Order,String>  columnProduct;
    @FXML
    private TableColumn<Order,String>  columnBrand;
    @FXML
    private TableColumn<Order,String>  columnQuantity;


    @FXML
    private TextField filterField;
    @FXML
    private Button exitButton, newOrderButton;

    ObservableList<Order> list = FXCollections.observableArrayList();
    FilteredList<Order> filterlist = new FilteredList<>(list,b-> true);






    @Override
    public void initialize(URL location, ResourceBundle resources) {

     fillEmployeeOrderTable();
     createFilteredListForEmployeeOrderTable();



        SortedList<Order> sortedList = new SortedList<>(filterlist);

        sortedList.comparatorProperty().bind(orderTable.comparatorProperty());

        orderTable.setItems(sortedList);





    }
    public void createFilteredListForEmployeeOrderTable(){

        //Wrap ObservableList in a FilteredList

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterlist.setPredicate(Order -> {

                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                //Compare User input with the name, brand, upc associated with employee order
                String lowerCaseValue = newValue.toLowerCase();


                if(Order.getEmployeeName().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Order.getOrderBrand().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Order.getOrderProduct().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Order.getOrderDate().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                } else if(Order.getOrderUpc().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else if(Order.getOrderQuantity().toLowerCase().indexOf(lowerCaseValue) != -1) {
                    return true;
                }
                else{
                    return false;
                }

            });
        });

        SortedList<Order> sortedList = new SortedList<>(filterlist);

        sortedList.comparatorProperty().bind(orderTable.comparatorProperty());

        orderTable.setItems(sortedList);
    }
    public void fillEmployeeOrderTable(){

        try {

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection con = connectNow.getConnection();

            ResultSet result = con.createStatement().executeQuery("select orderDate,firstname,brand,product.name,inventoryorder.upc, orderQuantity from inventoryorder\n" +
                    "inner join employee on inventoryorder.employeeID = employee.employeeID\n" +
                    "inner join product on inventoryorder.upc = product.upc order by orderDate;");

            while(result.next()){
                list.add(new Order(result.getString("orderDate"),
                        result.getString("firstname"), result.getString("brand"),
                        result.getString("name"), result.getString("upc"),result.getString("orderQuantity")));
            }
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
            Logger.getLogger(OrderSceneController.class.getName()).log(Level.SEVERE,null,e);

        }



        columnDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        columnBrand.setCellValueFactory(new PropertyValueFactory<>("orderBrand"));
        columnProduct.setCellValueFactory(new PropertyValueFactory<>("orderProduct"));
        columnUpc.setCellValueFactory(new PropertyValueFactory<>("orderUpc"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));



        orderTable.setItems(list);
    }


    public void exitButtonPressed(ActionEvent actionEvent)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreenScene.fxml"));
        Stage window =(Stage) exitButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));
    }
    public void newOrderButtonPressed(ActionEvent actionEvent)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CreateOrderScene.fxml"));
        Stage window =(Stage) newOrderButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,700));
    }
}



