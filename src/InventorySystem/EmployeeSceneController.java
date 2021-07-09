
///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            EmployeeSceneController.java
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


public class EmployeeSceneController implements Initializable {

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee,String>  columnName;
    @FXML
    private TableColumn<Employee,String>  columnTime;

    @FXML
    private TableColumn<Employee,String>  columnRole;
    @FXML
    private TableColumn<Employee,String>  columnContact;
    @FXML
    private TextField filterField;
    @FXML
    private Button exitButton;

    ObservableList<Employee> list = FXCollections.observableArrayList();
    FilteredList<Employee> filterList = new FilteredList<>(list,b-> true);






    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillEmployeeTable();
        createFilteredListForEmployeeTable();
    }

public void fillEmployeeTable(){

    try {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        //Query return list of all employees and employee information
        ResultSet result = con.createStatement().executeQuery("SELECT concat(firstname, ' ', lastname), jobcode  , workshift , email, employeeID FROM Employee order by workshift desc, jobcode,firstname;");

        while(result.next()){
            list.add(new Employee(result.getString("concat(firstname, ' ', lastname)"),
                    result.getString("email"), result.getString("jobcode"), result.getString("workshift")));
        }

    } catch(Exception e) {
        e.printStackTrace();
        e.getCause();
        Logger.getLogger(EmployeeSceneController.class.getName()).log(Level.SEVERE,null,e);

    }


    columnName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
    columnRole.setCellValueFactory(new PropertyValueFactory<>("employeeRole"));
    columnContact.setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
    columnTime.setCellValueFactory(new PropertyValueFactory<>("employeeShift"));

    employeeTable.setItems(list);




}

public void createFilteredListForEmployeeTable(){

    //Wrap ObservableList in a FilteredList

    filterField.textProperty().addListener((observable, oldValue, newValue) -> {
        filterList.setPredicate(employee -> {

            if(newValue.isEmpty() || newValue == null){
                return true;
            }
            //Compare user input with the names and roles of each employee
            String lowerCaseValue = newValue.toLowerCase();

            if(Employee.getEmployeeName().toLowerCase().indexOf(lowerCaseValue) != -1) {
                return true;
            }
            else if(Employee.getEmployeeRole().toLowerCase().indexOf(lowerCaseValue) != -1) {
                return true;
            }
            else{
                return false;
            }

        });
    });

    SortedList<Employee> sortedList = new SortedList<>(filterList);

    sortedList.comparatorProperty().bind(employeeTable.comparatorProperty());

    employeeTable.setItems(sortedList);

}
    public void exitButtonPressed(ActionEvent actionEvent)throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("HomeScreenScene.fxml"));
            Stage window =(Stage) exitButton.getScene().getWindow();
            window.setScene(new Scene(root, 800,700));
    }
}



