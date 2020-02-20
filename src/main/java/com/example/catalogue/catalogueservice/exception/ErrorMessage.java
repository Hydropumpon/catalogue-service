package com.example.catalogue.catalogueservice.exception;

public abstract class ErrorMessage {

    public static final String CATEGORY_NOT_FOUND = "Category not found";
    public static final String CATEGORY_EXIST = "Category already exist";
    public static final String ITEM_NOT_FOUND = "Item not found";
    public static final String MANUFACTURER_NOT_FOUND = "Manufacturer not found";
    public static final String MANUFACTURER_EXIST = "Manufacturer already exist";

    private ErrorMessage() {
    }

}
