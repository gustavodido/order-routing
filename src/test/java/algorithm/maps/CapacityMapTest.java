package algorithm.maps;

import org.junit.Before;
import org.junit.Test;
import providers.implementation.InMemoryWarehouseRepository;

import static algorithm.maps.CapacityMap.capacityMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static support.Constants.BRAZIL;

public class CapacityMapTest {
    private CapacityMap map;

    @Before
    public void setUp() throws Exception {
        map = capacityMap(new InMemoryWarehouseRepository().list());
    }

    @Test
    public void shouldReturnFalseIfWarehouseIsNotOverCapacity() throws Exception {
        assertThat(map.isOverCapacity(BRAZIL.getName()), is(false));
    }

    @Test
    public void shouldReturnTrueIfWarehouseIsOverCapacity() throws Exception {
        map.decreaseCapacity(BRAZIL.getName(), BRAZIL.getCapacity());
        assertThat(map.isOverCapacity(BRAZIL.getName()), is(true));
    }

    @Test
    public void shouldNotDecreaseCapacityToLessThanZero() throws Exception {
        map.decreaseCapacity(BRAZIL.getName(), BRAZIL.getCapacity() + 1);
        assertThat(map.getCapacity(BRAZIL.getName()), is(0));
    }

    @Test
    public void shouldDecreaseCapacityCorrectly() throws Exception {
        map.decreaseCapacity(BRAZIL.getName(), 3);
        assertThat(map.getCapacity(BRAZIL.getName()), is(12));
    }
}