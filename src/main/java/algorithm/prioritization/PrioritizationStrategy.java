package algorithm.prioritization;

import domain.Warehouse;
import domain.InventoryItem;

import java.util.List;

public interface PrioritizationStrategy {
    List<InventoryItem> prioritize(List<InventoryItem> inventoryItems, List<Warehouse> warehouses);
}
