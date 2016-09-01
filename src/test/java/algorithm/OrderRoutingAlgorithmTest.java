package algorithm;

import algorithm.dto.Request;
import algorithm.dto.Response;
import algorithm.filter.constraints.WarehouseConstraint;
import algorithm.prioritization.PrioritizationStrategy;
import algorithm.prioritization.strategies.NoPrioritizationStrategy;
import domain.InventoryItem;
import domain.OrderItem;
import domain.ShippingItem;
import domain.ShippingMethod;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import providers.implementation.InMemoryWarehouseRepository;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static support.Constants.*;

public class OrderRoutingAlgorithmTest {

    public static final ShippingItem SHIPPING_BR_MOUSE_4_ITEMS = new ShippingItem("Brazil", "Mouse", 4);
    public static final ShippingItem SHIPPING_FR_MOUSE_1_ITEM = new ShippingItem("France", "Mouse", 1);

    @Mock
    private WarehouseConstraint warehouseConstraint;

    @Mock
    private PrioritizationStrategy prioritizationStrategy;

    private OrderRoutingAlgorithm algorithm;

    @Before
    public void setUp() {
        initMocks(this);

        algorithm = new OrderRoutingAlgorithm(new InMemoryWarehouseRepository().list(), asList(warehouseConstraint));

        when(warehouseConstraint.build(any())).thenReturn(p -> true);
        when(prioritizationStrategy.prioritize(any(), any())).thenAnswer(invocation -> invocation.getArguments()[0]);
    }

    @Test
    public void shouldApplyConstraintsToWarehouses() {
        algorithm.execute(request());
        verify(warehouseConstraint).build(any());
    }


    @Test
    public void shouldApplyPrioritizationStrategy() {
        algorithm.execute(request());
        verify(prioritizationStrategy).prioritize(any(), any());
    }

    @Test
    public void shouldFulfillMultiItemOrder() {
        Request request = requestWith(asList(BR_MOUSE_4_ITEM, FR_MOUSE_2_ITEMS), asList(ORDER_MOUSE_5_ITEMS));
        Response response = algorithm.execute(request);

        assertThat(response.getShippingItems(), hasItems(SHIPPING_BR_MOUSE_4_ITEMS, SHIPPING_FR_MOUSE_1_ITEM));
    }

    private Request requestWith(List<InventoryItem> inventoryItems, List<OrderItem> orderItems) {
        return new Request(inventoryItems, ShippingMethod.DHL, new NoPrioritizationStrategy(), orderItems);
    }

    private Request request() {
        return new Request(emptyList(), ShippingMethod.DHL, prioritizationStrategy, emptyList());
    }
}