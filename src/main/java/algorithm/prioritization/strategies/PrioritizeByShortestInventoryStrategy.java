package algorithm.prioritization.strategies;

import algorithm.prioritization.PrioritizationStrategy;
import domain.InventoryItem;
import domain.Warehouse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrioritizeByShortestInventoryStrategy implements PrioritizationStrategy {
    @Override
    public List<InventoryItem> prioritize(List<InventoryItem> inventoryItems, List<Warehouse> warehouses) {
        Map<String, Integer> groupByInventory =
                inventoryItems.stream()
                        .collect(Collectors.groupingBy(InventoryItem::getWarehouseName,
                                Collectors.summingInt(InventoryItem::getQuantity)));

        Comparator<InventoryItem> sortByShortestInventory =
                Comparator
                        .comparing((InventoryItem i) -> groupByInventory.get(i.getWarehouseName()))
                        .thenComparing(InventoryItem::getQuantity);

        return inventoryItems.stream()
                .sorted(sortByShortestInventory)
                .collect(Collectors.toList());
    }
}
