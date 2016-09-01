package domain;

import static java.lang.String.format;

public class InventoryItem {
    private final String warehouseName;
    private final String productName;
    private final int quantity;

    public InventoryItem(String warehouseName, String productName, int quantity) {

        this.warehouseName = warehouseName;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return format("%s %s %s", warehouseName, productName, quantity);
    }
}
