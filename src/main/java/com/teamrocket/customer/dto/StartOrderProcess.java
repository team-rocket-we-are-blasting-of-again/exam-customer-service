package com.teamrocket.customer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CamundaStartOrderProcess {
    private OrderHolder variables;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class CamundaOrder {
        private String value;
        private String type;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class OrderHolder {
        private CamundaOrder order;
    }
}

