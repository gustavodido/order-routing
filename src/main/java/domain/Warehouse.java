package domain;

import java.util.List;

public class Warehouse {
    private String name;
    private final List<ShippingMethod> methods;
    private final int capacity;

    public Warehouse(String name, List<ShippingMethod> methods, int capacity) {
        this.name = name;
        this.methods = methods;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean hasShippingMethod(ShippingMethod shippingMethod) {
        return methods
                .stream()
                .anyMatch(s -> s == shippingMethod);
    }
}
