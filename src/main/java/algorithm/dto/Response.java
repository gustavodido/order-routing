package algorithm.dto;

import domain.ShippingItem;

import java.util.List;

public class Response {
    private final List<ShippingItem> shippingItems;
    private final boolean orderFulfilled;

    public Response(List<ShippingItem> shippingItems, boolean orderFulfilled) {
        this.shippingItems = shippingItems;
        this.orderFulfilled = orderFulfilled;
    }

    public List<ShippingItem> getShippingItems() {
        return shippingItems;
    }

    public boolean isOrderFulfilled() {
        return orderFulfilled;
    }
}
