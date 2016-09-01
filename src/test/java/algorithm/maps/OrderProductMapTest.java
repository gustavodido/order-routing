package algorithm.maps;

import org.junit.Before;
import org.junit.Test;

import static algorithm.maps.OrderProductMap.orderProductMap;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static support.Constants.*;

public class OrderProductMapTest {
    private OrderProductMap map;

    @Before
    public void setUp() throws Exception {
        map = orderProductMap(asList(ORDER_MOUSE_5_ITEMS));
    }

    @Test
    public void shouldReturnFalseIfProductIsNotFulfilled() throws Exception {
        assertThat(map.isProductFulfilled(MOUSE), is(false));
    }

    @Test
    public void shouldReturnTrueIfProductIsFulfilled() throws Exception {
        map.decreaseQuantity(MOUSE, 5);
        assertThat(map.isOrderFulfilled(), is(true));
    }

    @Test
    public void shouldReturnFalseIfOrderIsNotFulfilled() throws Exception {
        assertThat(map.isOrderFulfilled(), is(false));
    }

    @Test
    public void shouldReturnTrueIfOrderIsFulfilled() throws Exception {
        map.decreaseQuantity(MOUSE, 5);
        assertThat(map.isProductFulfilled(MOUSE), is(true));
    }

    @Test
    public void shouldNotDecreaseQuantityToLessThanZero() throws Exception {
        map.decreaseQuantity(MOUSE, 6);
        assertThat(map.getQuantity(MOUSE), is(0));
    }

    @Test
    public void shouldDecreaseQuantityCorrectly() throws Exception {
        map.decreaseQuantity(MOUSE, 3);
        assertThat(map.getQuantity(MOUSE), is(2));
    }
}