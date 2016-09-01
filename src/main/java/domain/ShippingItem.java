package domain;

import static java.lang.String.format;

public class ShippingItem {
    private final String distributionCenterName;
    private final String productName;
    private final int quantity;

    public ShippingItem(String distributionCenterName, String productName, int quantity) {

        this.distributionCenterName = distributionCenterName;
        this.productName = productName;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return  format("%s %s %s", distributionCenterName, productName, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        ShippingItem otherItem = (ShippingItem)obj;
        return toString().equals(otherItem.toString());
    }
}
