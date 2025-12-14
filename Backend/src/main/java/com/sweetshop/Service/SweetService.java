package com.sweetshop.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sweetshop.Exception.ResourceNotFoundException;
import com.sweetshop.Model.Sweet;
import com.sweetshop.Repository.SweetRepository;

import jakarta.transaction.Transactional;



@Service
public class SweetService {

	private final SweetRepository sweetRepository;

    public SweetService(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    public Sweet create(Sweet sweet) {
        // simple validation
        if (sweet.getPrice() == null || sweet.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
        if (sweet.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative");
        }
        return sweetRepository.save(sweet);
    }

    public Page<Sweet> list(int page, int size) {
        if (page < 0) page = 0;
        if (size <= 0) size = 20;
        return sweetRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Sweet> searchByName(String name, int page, int size) {
        List<Sweet> list = sweetRepository.findByNameContainingIgnoreCase(name == null ? "" : name);
        int p = Math.max(0, page);
        int s = Math.max(1, size);
        int start = Math.min(p * s, list.size());
        int end = Math.min(start + s, list.size());
        return new PageImpl<>(list.subList(start, end), PageRequest.of(p, s), list.size());
    }

//    @Transactional
//    public Sweet purchase(Integer id, int qty) {
//        if (qty <= 0) throw new IllegalArgumentException("Quantity must be at least 1");
//        Sweet sweet = sweetRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found: " + id));
//        if (sweet.getQuantity() < qty) {
//            throw new IllegalArgumentException("Not enough stock");
//        }
//        sweet.setQuantity(sweet.getQuantity() - qty);
//        return sweetRepository.save(sweet);
//    }
    
    
    public void purchase(int sweetId, int quantity) {

        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (quantity <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if (sweet.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        sweetRepository.save(sweet);
    }




    @Transactional
    public Sweet restock(Integer id, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be at least 1");
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found: " + id));
        sweet.setQuantity(sweet.getQuantity() + qty);
        return sweetRepository.save(sweet);
    }

    public Sweet update(Integer id, Sweet data) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found: " + id));
        if (data.getName() != null) sweet.setName(data.getName());
        sweet.setCategory(data.getCategory());
        if (data.getPrice() != null) {
            if (data.getPrice().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Price must be non-negative");
            sweet.setPrice(data.getPrice());
        }
        if (data.getQuantity() < 0) throw new IllegalArgumentException("Quantity must be non-negative");
        sweet.setQuantity(data.getQuantity());
        return sweetRepository.save(sweet);
    }

    public void delete(Integer id) {
        if (!sweetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sweet not found: " + id);
        }
        sweetRepository.deleteById(id);
    }

    public Optional<Sweet> findById(Integer id) {
        return sweetRepository.findById(id);
    }

	public Page<Sweet> searchByPrice(BigDecimal min, BigDecimal max, int page, int size) {
    if (min == null || max == null) {
        throw new IllegalArgumentException("Both min and max prices are required");
    }

    List<Sweet> list = sweetRepository.findByPriceBetween(min, max);

    int p = Math.max(0, page);
    int s = Math.max(1, size);

    int start = Math.min(p * s, list.size());
    int end = Math.min(start + s, list.size());

    return new PageImpl<>(list.subList(start, end), PageRequest.of(p, s), list.size());
}
	



}
