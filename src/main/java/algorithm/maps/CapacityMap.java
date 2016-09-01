package algorithm.maps;

import domain.Warehouse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class CapacityMap {
    private final Map<String, Integer> capacityMap;

    private CapacityMap(List<Warehouse> warehouses) {
        capacityMap = warehouses.stream()
                .collect(Collectors.toMap(Warehouse::getName, Warehouse::getCapacity));
    }


    public boolean isOverCapacity(String productName) {
        return getCapacity(productName) == 0;
    }

    public int getCapacity(String productName) {
        return capacityMap.getOrDefault(productName, 0);
    }

    public void decreaseCapacity(String productName, int quantity) {
        int currentQuantity = capacityMap.get(productName);
        capacityMap.replace(productName, max(currentQuantity - quantity, 0));
    }

    public static CapacityMap capacityMap(List<Warehouse> warehouses) {
        return new CapacityMap(warehouses);
    }

}
