package algorithm.prioritization.strategies;

import algorithm.prioritization.PrioritizationStrategy;
import domain.InventoryItem;
import domain.Warehouse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrioritizeByLargestCapacityStrategy implements PrioritizationStrategy {
    @Override
    public List<InventoryItem> prioritize(List<InventoryItem> inventoryItems, List<Warehouse> warehouses) {
        Map<String, Integer> groupByCapacity =
                warehouses.stream()
                        .collect(Collectors.groupingBy(Warehouse::getName,
                                Collectors.summingInt(Warehouse::getCapacity)));


        Comparator<InventoryItem> sortByCapacity =
                Comparator
                        .comparing((InventoryItem i) -> groupByCapacity.get(i.getWarehouseName()))
                        .thenComparing(InventoryItem::getQuantity)
                        .reversed();

        return inventoryItems.stream()
                .sorted(sortByCapacity)
                .collect(Collectors.toList());
    }
}
