package com.sweetshop.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data

public class PurchaseRequest {
    private int quantity;

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
