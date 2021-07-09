///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            Employee.java
// Date:            June 2021
//
// Author:          Ryan Jordan Roberts
/*
 * This Application manages inventory for a shoe store company.
 * Shoe stor employees are able to Login and see list of
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



public class Employee {

   static String employeeName;
    String employeeContact;
   static String employeeRole;
    String employeeShift;





    public Employee(String name, String contact, String role, String shift ){

        this.employeeName = name;
        this. employeeContact = contact;
        this.employeeRole = role;
        this.employeeShift = shift;
    }



    public static String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeContact() {
        return employeeContact;
    }

    public void setEmployeeContact(String employeeContact) {
        this.employeeContact = employeeContact;
    }

    public static   String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getEmployeeShift() {
        return employeeShift;
    }

    public void setEmployeeShift(String employeeShift) {
        this.employeeShift = employeeShift;
    }
}
