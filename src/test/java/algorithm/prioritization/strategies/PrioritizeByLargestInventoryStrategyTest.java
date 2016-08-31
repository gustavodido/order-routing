package algorithm.prioritization.strategies;

import domain.InventoryItem;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static support.Constants.*;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PrioritizeByLargestInventoryStrategyTest {

    private PrioritizeByLargestInventoryStrategy strategy;

    @Before
    public void setUp() {
        strategy = new PrioritizeByLargestInventoryStrategy();
    }

    @Test
    public void shouldPrioritizeWarehousesWithMoreInventory() throws Exception {
        List<InventoryItem> list = asList(FR_MOUSE_2_ITEMS, CN_MOUSE_1_ITEM, BR_MONITOR_5_ITEMS, BR_MOUSE_4_ITEM);
        List<InventoryItem> prioritizedList = strategy.prioritize(list, null);

        assertThat(asList(BR_MONITOR_5_ITEMS, BR_MOUSE_4_ITEM, FR_MOUSE_2_ITEMS, CN_MOUSE_1_ITEM), is(prioritizedList));
    }
}