package com.backend.app.shared.models.entities;


public enum OrderStatus {
    AWAITING_PAYMENT,
    AWAITING_FULLFILLMENT,
    REFUNDED,
    AWAITING_SHIPMENT,
    SHIPPING,
    COMPLETED,
    CANCELLED_BY_CUSTOMER,
    CANCELLED_BY_SYSTEM;
}
