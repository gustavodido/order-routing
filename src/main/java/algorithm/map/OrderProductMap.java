package algorithm.map;

import domain.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderProductMap {
    private final Map<String, Integer> productMap;

    public OrderProductMap(List<OrderItem> orderItems) {
        productMap =
                orderItems.stream()
                        .collect(Collectors.groupingBy(
                                OrderItem::getProductName,
                                Collectors.summingInt(OrderItem::getQuantity)
                        ));
    }


    public boolean isFulfilled(String productName) {
        return productMap.getOrDefault(productName, 0) == 0;
    }

    public int shipForQuantity(String productName, int quantity) {
        int currentQuantity = productMap.get(productName);
        int quantityToSource = Math.min(currentQuantity, quantity);

        productMap.replace(productName, currentQuantity - quantityToSource);

        return quantityToSource;
    }
}
