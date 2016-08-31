package algorithm.filter.constraints;

import algorithm.dto.Request;
import domain.Warehouse;

import java.util.function.Predicate;

public interface WarehouseConstraint {
    Predicate<Warehouse> build(Request request);
}
