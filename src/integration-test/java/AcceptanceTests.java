import algorithm.OrderRoutingAlgorithm;
import algorithm.dto.Request;
import algorithm.dto.Response;
import algorithm.filter.constraints.constraints.ShippingMethodConstraint;
import algorithm.prioritization.strategies.NoPrioritizationStrategy;
import algorithm.prioritization.strategies.PrioritizeByLargestCapacityStrategy;
import algorithm.prioritization.strategies.PrioritizeByLargestInventoryStrategy;
import algorithm.prioritization.strategies.PrioritizeByShortestInventoryStrategy;
import domain.InventoryItem;
import domain.OrderItem;
import domain.ShippingItem;
import domain.ShippingMethod;
import org.junit.Before;
import org.junit.Test;
import repositories.implementation.InMemoryWarehouseRepository;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AcceptanceTests {

    private OrderRoutingAlgorithm algorithm;

    @Before
    public void setUp() {
        algorithm = new OrderRoutingAlgorithm(new InMemoryWarehouseRepository().list(), singletonList(new ShippingMethodConstraint()));
    }

    @Test
    public void standardCase() {
        Request request = new Request(asList(
                new InventoryItem("Brazil", "Keyboard", 2),
                new InventoryItem("France", "Mouse", 2)),
                ShippingMethod.DHL,
                new NoPrioritizationStrategy(),
                singletonList(
                        new OrderItem("Keyboard", 2)
                )
        );

        Response response = algorithm.execute(request);

        assertThat(response.getShippingItems(), hasItems(new ShippingItem("Brazil", "Keyboard", 2)));
    }

    @Test
    public void shippingMethod() {
        Request request = new Request(asList(
                new InventoryItem("Brazil", "Mouse", 2),
                new InventoryItem("South Africa", "Mouse", 2)),
                ShippingMethod.UPS,
                new NoPrioritizationStrategy(),
                singletonList(
                        new OrderItem("Mouse", 1)
                )
        );

        Response response = algorithm.execute(request);

        assertThat(response.getShippingItems(), hasItems(new ShippingItem("South Africa", "Mouse", 1)));
    }

    @Test
    public void capacity() {
        Request request = new Request(asList(
                new InventoryItem("Canada", "Mouse", 4),
                new InventoryItem("Canada", "Keyboard", 3),
                new InventoryItem("France", "Keyboard", 2)),
                ShippingMethod.Fedex,
                new NoPrioritizationStrategy(),
                asList(
                        new OrderItem("Mouse", 4),
                        new OrderItem("Keyboard", 3)
                )
        );

        Response response = algorithm.execute(request);

        assertThat(response.getShippingItems(), hasItems(
                new ShippingItem("Canada", "Mouse", 4),
                new ShippingItem("Canada", "Keyboard", 1),
                new ShippingItem("France", "Keyboard", 2)
        ));
    }

    @Test
    public void prioritizeByLargestInventory() {
        Request request = new Request(asList(
                new InventoryItem("China", "Mouse", 4),
                new InventoryItem("Brazil", "Mouse", 3),
                new InventoryItem("Brazil", "Keyboard", 3),
                new InventoryItem("France", "Mouse", 2),
                new InventoryItem("France", "Keyboard", 2)),
                ShippingMethod.DHL,
                new PrioritizeByLargestInventoryStrategy(),
                asList(
                        new OrderItem("Mouse", 1),
                        new OrderItem("Keyboard", 1)
                )
        );

        Response response = algorithm.execute(request);

        assertThat(response.getShippingItems(), hasItems(
                new ShippingItem("Brazil", "Mouse", 1),
                new ShippingItem("Brazil", "Keyboard", 1)
        ));
    }

    @Test
    public void prioritizeByShortestInventory() {
        Request request = new Request(asList(
                new InventoryItem("China", "Mouse", 4),
                new InventoryItem("Brazil", "Mouse", 3),
                new InventoryItem("Brazil", "Keyboard", 3),
                new InventoryItem("France", "Keyboard", 2)),
                ShippingMethod.DHL,
                new PrioritizeByShortestInventoryStrategy(),
                asList(
                        new OrderItem("Mouse", 1),
                        new OrderItem("Keyboard", 1)
                )
        );

        Response response = algorithm.execute(request);

        assertThat(response.getShippingItems(), hasItems(
                new ShippingItem("China", "Mouse", 1),
                new ShippingItem("France", "Keyboard", 1)
        ));
    }

    @Test
    public void prioritizeByLargestCapacity() {
        Request request = new Request(asList(
                new InventoryItem("China", "Mouse", 4),
                new InventoryItem("Brazil", "Mouse", 3),
                new InventoryItem("Brazil", "Keyboard", 3),
                new InventoryItem("France", "Mouse", 2),
                new InventoryItem("France", "Keyboard", 2)),
                ShippingMethod.DHL,
                new PrioritizeByLargestCapacityStrategy(),
                asList(
                        new OrderItem("Mouse", 1),
                        new OrderItem("Keyboard", 1)
                )
        );

        Response response = algorithm.execute(request);

        assertThat(response.getShippingItems(), hasItems(
                new ShippingItem("China", "Mouse", 1),
                new ShippingItem("Brazil", "Keyboard", 1)
        ));
    }

    @Test
    public void manyProducts() {
        Request request = new Request(asList(
                new InventoryItem("Canada", "Mouse", 2),
                new InventoryItem("Brazil", "Mouse", 2),
                new InventoryItem("Brazil", "Keyboard", 3),
                new InventoryItem("France", "Keyboard", 2),
                new InventoryItem("South Africa", "Monitor", 4),
                new InventoryItem("South Africa", "Camera", 1),
                new InventoryItem("South Africa", "Mouse", 2)),
                ShippingMethod.Fedex,
                new NoPrioritizationStrategy(),
                asList(
                        new OrderItem("Mouse", 6),
                        new OrderItem("Keyboard", 3),
                        new OrderItem("Monitor", 3),
                        new OrderItem("Camera", 1)
                )
        );

        Response response = algorithm.execute(request);

        assertThat(response.getShippingItems(), hasItems(
                new ShippingItem("Canada", "Mouse", 2),
                new ShippingItem("Brazil", "Mouse", 2),
                new ShippingItem("Brazil", "Keyboard", 3),
                new ShippingItem("South Africa", "Mouse", 2),
                new ShippingItem("South Africa", "Monitor", 3),
                new ShippingItem("South Africa", "Camera", 1)
        ));
    }

    @Test
    public void invalid() {
        Request request = new Request(asList(
                new InventoryItem("China", "Mouse", 4),
                new InventoryItem("Brazil", "Mouse", 3)),
                ShippingMethod.Fedex,
                new NoPrioritizationStrategy(),
                singletonList(
                        new OrderItem("Mouse", 5)
                )
        );

        Response response = algorithm.execute(request);

        assertThat(response.isOrderFulfilled(), is(false));
    }


}
