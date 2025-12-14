package com.sweetshop.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sweetshop.Model.Sweet;

public interface SweetRepository extends JpaRepository<Sweet, Integer> {

	// Search by name (case-insensitive)
    List<Sweet> findByNameContainingIgnoreCase(String name);

    // Filter by category
    List<Sweet> findByCategoryIgnoreCase(String category);

    // Price range filter
    List<Sweet> findByPriceBetween(BigDecimal min, BigDecimal max);

//	Sweet getSweet(int id);
}
