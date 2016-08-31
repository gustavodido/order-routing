package algorithm.filter.constraints;

import algorithm.dto.Request;
import algorithm.filter.constraints.constraints.CapacityConstraint;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static support.Constants.*;

public class CapacityConstraintTest {
    private CapacityConstraint constraint;

    @Before
    public void setUp() {
        constraint = new CapacityConstraint();
    }

    @Test
    public void shouldBeTrueIfRequestInUnderCapacity() {
        Request request = new Request(asList(BR_MONITOR_5_ITEMS, BR_MOUSE_4_ITEM, FR_MOUSE_2_ITEMS), null, null, null);

        assertThat(constraint.build(request).test(BRAZIL), is(true));
    }

    @Test
    public void shouldBeFalseIfRequestInOverCapacity() {
        Request request = new Request(asList(BR_MONITOR_5_ITEMS, BR_MOUSE_4_ITEM, FR_MOUSE_2_ITEMS, BR_KEYBOARD_3_ITEMS), null, null, null);

        assertThat(constraint.build(request).test(BRAZIL), is(false));
    }
}