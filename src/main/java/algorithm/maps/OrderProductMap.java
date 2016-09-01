package algorithm.maps;

import domain.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.util.stream.Collectors.summingInt;

public class OrderProductMap {
    private final Map<String, Integer> productMap;

    private OrderProductMap(List<OrderItem> orderItems) {
        productMap =
                orderItems.stream()
                        .collect(Collectors.groupingBy(
                                OrderItem::getProductName,
                                summingInt(OrderItem::getQuantity)
                        ));
    }


    public boolean isProductFulfilled(String productName) {
        return getQuantity(productName) == 0;
    }

    public boolean isOrderFulfilled() {
        return productMap.values().stream().reduce(0, Integer::sum) == 0;
    }

    public int getQuantity(String productName) {
        return productMap.getOrDefault(productName, 0);
    }

    public void decreaseQuantity(String productName, int quantity) {
        int currentQuantity = productMap.get(productName);
        productMap.replace(productName, max(currentQuantity - quantity, 0));
    }

    public static OrderProductMap orderProductMap(List<OrderItem> orderItems) {
        return new OrderProductMap(orderItems);
    }
}
