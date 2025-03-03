package com.crm.springbootjwtimplementation.domain;

public enum DealStage {
    DEAL_CREATION("Deal Creation"),
    DEAL_INITIATION("Deal Initiation"),
    ADMIN_APPROVAL("Admin Approval"),
    DELIVERY_ASSIGNMENT("Delivery Assignment"),
    ORDER_DELIVERY("Order Delivery"),
    PAYMENT_CONFIRMATION("Payment Confirmation"),
    DEAL_CLOSURE("Deal Closure");

    private final String displayName;

    DealStage(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
