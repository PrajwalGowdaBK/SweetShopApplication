package com.sweetshop.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "sweets", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;
}



