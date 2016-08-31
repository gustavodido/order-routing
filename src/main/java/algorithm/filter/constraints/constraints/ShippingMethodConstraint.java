package algorithm.filter.constraints.constraints;

import algorithm.dto.Request;
import algorithm.filter.constraints.WarehouseConstraint;
import domain.Warehouse;

import java.util.function.Predicate;

public class ShippingMethodConstraint implements WarehouseConstraint {
    @Override
    public Predicate<Warehouse> build(Request request) {
        return warehouse -> warehouse.hasShippingMethod(request.getShippingMethod());
    }
}
