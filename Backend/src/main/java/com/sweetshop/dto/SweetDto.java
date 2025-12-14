package com.sweetshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SweetDto {

    private Integer id;

    @NotBlank
    private String name;

    private String category;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @Min(0)
    private int quantity;
}

