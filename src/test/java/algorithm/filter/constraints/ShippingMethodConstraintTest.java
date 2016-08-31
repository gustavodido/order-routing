package algorithm.filter.constraints;


import algorithm.dto.Request;
import algorithm.filter.constraints.constraints.ShippingMethodConstraint;
import org.junit.Before;
import org.junit.Test;
import support.Constants;

import static domain.ShippingMethod.DHL;
import static domain.ShippingMethod.UPS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ShippingMethodConstraintTest {

    private ShippingMethodConstraint constraint;

    @Before
    public void setUp() {
        constraint = new ShippingMethodConstraint();
    }

    @Test
    public void shouldBeTrueIfShippingMethodIsSupported() {
        Request request = new Request(null, DHL, null, null);

        assertThat(constraint.build(request).test(Constants.BRAZIL), is(true));
    }

    @Test
    public void shouldBeFalseIfShippingMethodIsNotSupported() {
        Request request = new Request(null, UPS, null, null);

        assertThat(constraint.build(request).test(Constants.BRAZIL), is(false));
    }
}