package algorithm.prioritization.strategies;

import algorithm.prioritization.PrioritizationStrategy;
import domain.InventoryItem;
import domain.Warehouse;

import java.util.List;

public class NoPrioritizationStrategy implements PrioritizationStrategy{
    @Override
    public List<InventoryItem> prioritize(List<InventoryItem> inventoryItems, List<Warehouse> warehouses) {
        return inventoryItems;
    }
}
