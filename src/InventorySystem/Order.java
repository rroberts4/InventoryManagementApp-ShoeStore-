///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            Order.java
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

public class Order {

    String orderDate;
    String employeeName;
    String orderBrand;
    String orderProduct;
    String orderUpc;
    String orderQuantity;






    public Order(String date, String name, String brand, String product, String upc, String quantity){

        this.orderDate = date;
        this.employeeName = name;
        this.orderBrand = brand;
        this.orderProduct = product;
        this.orderQuantity = quantity;
        this.orderUpc = upc;


    }


    public  String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }


    public  String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


    public  String getOrderBrand() {
        return orderBrand;
    }

    public void setOrderBrand(String orderBrand) {
        this.orderBrand = orderBrand;
    }


    public String getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(String orderProduct) {
        this.orderProduct = orderProduct;
    }

    public String getOrderUpc() {
        return orderUpc;
    }

    public void setOrderUpc(String orderUpc) {
        this.orderUpc = orderUpc;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
