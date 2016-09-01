package providers.implementation;

import java.util.List;

import domain.Warehouse;
import providers.WarehouseRepository;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import static domain.ShippingMethod.DHL;
import static domain.ShippingMethod.Fedex;
import static domain.ShippingMethod.UPS;

public class InMemoryWarehouseRepository implements WarehouseRepository {

    private static final List<Warehouse> WAREHOUSES = asList(
            new Warehouse("Brazil", asList(DHL, Fedex), 15),
            new Warehouse("France", asList(DHL, Fedex, UPS), 10),
            new Warehouse("South Africa", asList(Fedex, UPS), 10),
            new Warehouse("China", singletonList(DHL), 20),
            new Warehouse("Canada", singletonList(Fedex), 5)
    );

    @Override
    public List<Warehouse> list() {
        return WAREHOUSES;
    }
}

