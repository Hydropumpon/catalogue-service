package com.example.catalogue.catalogueservice.logging.messages;

public class ItemControllerLogMessages {

    public static final String ADD_ITEM = "New item creation requested: {}";
    public static final String GET_ONE_ITEM = "Item information requested for: {}";
    public static final String GET_ALL_ITEMS = "All items information requested";
    public static final String DELETE_ITEM = "Item delete requested for: {}";
    public static final String UPDATE_ITEM = "Item update requested for id: {} and information: {}";
    public static final String ITEM_ADD_CATEGORIES = "Adding categories to item: {} and categories :{}";
    public static final String ITEM_DELETE_CATEGORIES = "Delete categories requested for item: {}";
    public static final String ITEM_GET_CATEGORIES = "Item categories information request for item: {}";
    public static final String GET_ITEMS_BY_CATEGORY = "Items information requested for category: {}";
    public static final String GET_ITEMS_BY_NAME = "Items information requested for name: {}";
    public static final String GET_ITEMS_BY_CATEGORY_AND_MANUFACTURER =
            "Items information requested for category: {} and manufacturer: {}";

    private ItemControllerLogMessages() {
    }
}
