package com.teamrocket.customer.domain.model.dto;

import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MenuItemDTO {
    private String name;
    private String description;
    private List<CustomerOrderEntity> items;
}
