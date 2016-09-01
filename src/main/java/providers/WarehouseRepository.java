package providers;

import domain.Warehouse;

import java.util.List;

public interface WarehouseRepository {
    List<Warehouse> list();
}
