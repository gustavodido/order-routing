package support;

import domain.InventoryItem;
import domain.OrderItem;
import domain.Warehouse;
import providers.implementation.InMemoryWarehouseRepository;

import java.util.List;

import static domain.ShippingMethod.DHL;
import static domain.ShippingMethod.Fedex;
import static java.util.Arrays.asList;

public class Constants {
    public static final List<Warehouse> WAREHOUSES = new InMemoryWarehouseRepository().list();

    public static final InventoryItem FR_MOUSE_2_ITEMS = new InventoryItem("France", "Mouse", 2);
    public static final InventoryItem CN_MOUSE_1_ITEM = new InventoryItem("China", "Mouse", 1);
    public static final InventoryItem BR_MONITOR_5_ITEMS = new InventoryItem("Brazil", "Monitor", 5);
    public static final InventoryItem BR_MOUSE_4_ITEM = new InventoryItem("Brazil", "Mouse", 4);
    public static final InventoryItem BR_KEYBOARD_3_ITEMS = new InventoryItem("Brazil", "Keyboard", 3);;

    public static final Warehouse BRAZIL = new Warehouse("Brazil", asList(Fedex, DHL), 15);

    public static final OrderItem ORDER_MOUSE_5_ITEMS = new OrderItem("Mouse", 5);
    public static final OrderItem ORDER_MONITOR_4_ITEMS = new OrderItem("Monitor", 4);
    public static final OrderItem ORDER_KEYBOARD_3_ITEMS = new OrderItem("Keyboard", 3);

    public static final String MOUSE = "Mouse";

}
