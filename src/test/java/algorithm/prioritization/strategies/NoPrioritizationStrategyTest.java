package algorithm.prioritization.strategies;

import domain.InventoryItem;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static support.Constants.*;

public class NoPrioritizationStrategyTest {
    private NoPrioritizationStrategy strategy;

    @Before
    public void setUp() {
        strategy = new NoPrioritizationStrategy();
    }

    @Test
    public void shouldPrioritizeWarehousesWithHigherCapacity() throws Exception {
        List<InventoryItem> list = asList(FR_MOUSE_2_ITEMS, CN_MOUSE_1_ITEM, BR_MONITOR_5_ITEMS);
        List<InventoryItem> prioritizedList = strategy.prioritize(list, WAREHOUSES);

        assertThat(list, is(prioritizedList));
    }

}