package com.teamrocket.customer.service;

import com.teamrocket.customer.model.entity.CartEntity;

public interface IRestaurantService {
    double restaurantCalculateTotalPrice(CartEntity entity);
}
