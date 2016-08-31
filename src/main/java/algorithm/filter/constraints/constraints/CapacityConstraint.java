package algorithm.filter.constraints.constraints;

import algorithm.dto.Request;
import algorithm.filter.constraints.WarehouseConstraint;
import domain.InventoryItem;
import domain.Warehouse;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CapacityConstraint implements WarehouseConstraint {

    @Override
    public Predicate<Warehouse> build(Request request) {
        Map<String, Integer> capacityMap =
                request.getAvailableItems().stream()
                        .collect(Collectors.groupingBy(
                                InventoryItem::getWarehouseName,
                                Collectors.summingInt(InventoryItem::getQuantity)
                        ));

        return warehouse ->
                warehouse.getCapacity() > capacityMap.getOrDefault(warehouse.getName(), Integer.MAX_VALUE);
    }
}
