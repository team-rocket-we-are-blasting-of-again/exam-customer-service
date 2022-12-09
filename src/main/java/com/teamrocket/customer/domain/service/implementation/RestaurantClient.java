package com.teamrocket.customer.domain.service.implementation;

import com.teamrocket.proto.Order;
import com.teamrocket.proto.OrderItem;
import com.teamrocket.proto.RestaurantGrpc;
import com.teamrocket.customer.domain.model.entity.CartEntity;
import com.teamrocket.customer.domain.model.entity.CartItemEntity;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RestaurantClient {
    @GrpcClient("grpc-restaurant-service")
    private RestaurantGrpc.RestaurantBlockingStub synchronousCall;

    public double restaurantCalculateTotalPrice(CartEntity entity) {
        Order.Builder orderBuilder = Order.newBuilder()
                .setRestaurantId(entity.getRestaurantId())
                .setTotalPrice(entity.getTotalPrice());

        List<OrderItem> itemList = new ArrayList();

        log.info("Cart entity used to build an order for restaurant: " + entity);
        for (int i = 0; i < entity.getItems().size(); i++) {
            CartItemEntity cartItemEntity = entity.getItems().get(i);
            OrderItem orderItemRequest = OrderItem.newBuilder()
                    .setMenuItemId(cartItemEntity.getMenuItemId())
                    .setQuantity(cartItemEntity.getQuantity())
                    .setPrice(0.00)
                    .build();
            itemList.add(orderItemRequest);
        }
        Iterable<OrderItem> itemIterable = itemList;
        orderBuilder.addAllItems(itemIterable);
        log.info("OrderRequest passed to restaurant: " + orderBuilder);
        Order response = synchronousCall.calculateOrderPrice(orderBuilder.build());
        log.info("Calculated Cart: ", response.toString());
        return response.getTotalPrice();
    }
}
