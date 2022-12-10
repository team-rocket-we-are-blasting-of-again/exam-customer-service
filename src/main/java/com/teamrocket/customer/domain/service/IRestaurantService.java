package com.teamrocket.customer.domain.service;

import com.teamrocket.customer.domain.model.entity.CartEntity;

public interface IRestaurantService {
    double restaurantCalculateTotalPrice(CartEntity entity);
}
