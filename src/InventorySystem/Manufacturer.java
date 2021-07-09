///////////////////////////////////////////////////////////////////////////////
//
// Title:           InventoryManagementDB
// Main Class File: InventoryManagementDB.Main.java
// File:            Manufacturer.java
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

public class Manufacturer {


    String manufacturerDate;
    String manufacturerName;
    String manufacturerId;
    String manufacturerProduct;
    String manufacturerQuantity;
    String productUpc;



    public Manufacturer(String date, String name, String id, String product, String upc, String quantity) {
        this.manufacturerName = name;
        this.manufacturerId = id;
        this.manufacturerDate = date;
        this.manufacturerProduct = product;
        this.manufacturerQuantity = quantity;
        this.productUpc = upc;


    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerDate() {
        return manufacturerDate;
    }

    public void setManufacturerDate(String manufacturerDate) {
        this.manufacturerDate = manufacturerDate;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerProduct() {
        return manufacturerProduct;
    }

    public void setManufacturerProduct(String manufacturerProduct) {
        this.manufacturerProduct = manufacturerProduct;
    }

    public String getManufacturerQuantity() {
        return manufacturerQuantity;
    }

    public void setManufacturerQuantity(String manufacturerQuantity) {
        this.manufacturerQuantity = manufacturerQuantity;
    }

    public String getProductUpc() {
        return productUpc;
    }

    public void setProductUpc(String productUpc) {
        this.productUpc = productUpc;
    }
}