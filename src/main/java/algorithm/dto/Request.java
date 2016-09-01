package algorithm.dto;

import algorithm.prioritization.PrioritizationStrategy;
import domain.InventoryItem;
import domain.OrderItem;
import domain.ShippingMethod;

import java.util.List;

public class Request {
    private final List<InventoryItem> availableItems;
    private final ShippingMethod shippingMethod;
    private final PrioritizationStrategy prioritizationStrategy;
    private final List<OrderItem> orderItems;

    public Request(List<InventoryItem> availableItems,
                   ShippingMethod shippingMethod,
                   PrioritizationStrategy prioritizationStrategy,
                   List<OrderItem> orderItems) {

        this.availableItems = availableItems;
        this.shippingMethod = shippingMethod;
        this.prioritizationStrategy = prioritizationStrategy;
        this.orderItems = orderItems;
    }

    public List<InventoryItem> getAvailableItems() {
        return availableItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public PrioritizationStrategy getPrioritizationStrategy() {
        return prioritizationStrategy;
    }
}
