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

        for (InventoryItem i : prioritizedInventory) {
            if (!orderProductMap.isProductFulfilled(i.getProductName()) && !capacityMap.isOverCapacity(i.getWarehouseName())) {
                int quantityBefore = orderProductMap.getQuantity(i.getProductName());
                int capacityQuantity = capacityMap.getCapacity(i.getWarehouseName());

                orderProductMap.decreaseQuantity(i.getProductName(), min(i.getQuantity(), capacityQuantity));
                int quantity = min(min(i.getQuantity(), capacityQuantity), quantityBefore);
                capacityMap.decreaseCapacity(i.getWarehouseName(), quantity);

                shippingItems.add(new ShippingItem(i.getWarehouseName(), i.getProductName(), quantity));
            }
        }

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
