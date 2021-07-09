///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            Inventory.java
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

public class Inventory {
    String productBrand;
    String productName;
    String productSize;
    String productColor;
    String productUpc;
    String productCount;
    String productPrice;





    public Inventory(String productBrand, String productName, String productSize, String productColor, String productCount, String productUpc, String productPrice){

        this.productBrand= productBrand;
        this.productColor = productColor;
        this.productName = productName;
        this.productSize = productSize;
        this.productCount = productCount;
        this.productUpc = productUpc;
        this.productPrice = productPrice;

    }



    public  String getProductBrand() {

        return productBrand;
    }

    public void setProductBrand(String brand) {

        this.productBrand = brand;
    }

    public  String getProductPrice() {

        return productPrice;
    }

    public void setProductPrice(String price) {

        this.productPrice = price;
    }



    public String getProductName() {

        return productName;
    }

    public void setProductName(String name) {

        this.productName = name;
    }



    public  String getProductSize() {

        return productSize;
    }

    public void setProductSize(String size) {

        this.productSize = size;
    }



    public String getProductColor() {

        return productColor;
    }

    public void setProductColor(String color) {


        this.productColor = color;
    }

    public String getProductCount() {

        return productCount;
    }

    public void setProductCount(String count) {

        this.productCount = count;
    }

    public String getProductUpc() {

        return productUpc;
    }

    public void setProductUpc(String upc) {


        this.productUpc = upc;
    }
}
