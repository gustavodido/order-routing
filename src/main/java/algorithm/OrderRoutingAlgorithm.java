package algorithm;

import algorithm.filter.constraints.WarehouseConstraint;
import algorithm.dto.Request;
import algorithm.dto.Response;
import algorithm.maps.CapacityMap;
import algorithm.maps.OrderProductMap;
import domain.Warehouse;
import domain.InventoryItem;
import domain.ShippingItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static algorithm.maps.CapacityMap.capacityMap;
import static algorithm.maps.OrderProductMap.orderProductMap;
import static java.lang.Math.min;

public class OrderRoutingAlgorithm {

    private final List<Warehouse> warehouses;
    private final List<WarehouseConstraint> constraints;

    public OrderRoutingAlgorithm(List<Warehouse> warehouses, List<WarehouseConstraint> constraints) {
        this.warehouses = warehouses;
        this.constraints = constraints;
    }

    public Response execute(Request request) {
        List<Warehouse> availableWarehouses = getAvailableWarehouses(request);

        List<InventoryItem> availableInventory = request.getAvailableItems().stream()
                .filter(byAvailableWarehouses(availableWarehouses))
                .collect(Collectors.toList());

        List<InventoryItem> prioritizedInventory = request
                .getPrioritizationStrategy()
                .prioritize(availableInventory, availableWarehouses);

        return fulfillOrder(
                orderProductMap(request.getOrderItems()),
                capacityMap(availableWarehouses),
                prioritizedInventory);
    }

    private Response fulfillOrder(OrderProductMap orderProductMap, CapacityMap capacityMap, List<InventoryItem> prioritizedInventory) {
        List<ShippingItem> shippingItems = new ArrayList<>();

        Predicate<InventoryItem> inventoryItemPredicate = item ->
                !orderProductMap.isProductFulfilled(item.getProductName()) &&
                        !capacityMap.isOverCapacity(item.getWarehouseName());

        prioritizedInventory.stream()
                .filter(inventoryItemPredicate)
                .forEach(item -> {
                    int currentQuantity = orderProductMap.getQuantity(item.getProductName());
                    int capacity = capacityMap.getCapacity(item.getWarehouseName());
                    int availableQuantity = min(min(item.getQuantity(), capacity), currentQuantity);

                    orderProductMap.decreaseQuantity(item.getProductName(), min(item.getQuantity(), capacity));
                    capacityMap.decreaseCapacity(item.getWarehouseName(), availableQuantity);

                    shippingItems.add(new ShippingItem(item.getWarehouseName(), item.getProductName(), availableQuantity));
                });

        return new Response(shippingItems, orderProductMap.isOrderFulfilled());
    }

    private Predicate<InventoryItem> byAvailableWarehouses(List<Warehouse> availableWarehouses) {
        return p -> availableWarehouses.stream().anyMatch(dc -> dc.getName().equals(p.getWarehouseName()));
    }

    private List<Warehouse> getAvailableWarehouses(Request request) {
        return warehouses
                .stream()
                .filter(constraints.stream()
                        .map(c -> c.build(request))
                        .reduce(p -> true, Predicate::and))
                .collect(Collectors.toList());
    }
}
