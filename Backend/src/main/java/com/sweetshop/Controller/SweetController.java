package com.sweetshop.Controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sweetshop.Model.Sweet;
import com.sweetshop.Service.SweetService;
import com.sweetshop.dto.PurchaseRequest;
import com.sweetshop.dto.SweetDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

	    private final SweetService sweetService;

	    public SweetController(SweetService sweetService) {
	        this.sweetService = sweetService;
	    }

	    // Convert Sweet entity → DTO
	    private SweetDto toDto(Sweet sweet) {
	        SweetDto dto = new SweetDto();
	        dto.setId(sweet.getId());
	        dto.setName(sweet.getName());
	        dto.setCategory(sweet.getCategory());
	        dto.setPrice(sweet.getPrice());
	        dto.setQuantity(sweet.getQuantity());
	        return dto;
	    }

	    // Convert DTO → Sweet entity
	    private Sweet toEntity(SweetDto dto) {
	        Sweet sweet = new Sweet();
	        sweet.setName(dto.getName());
	        sweet.setCategory(dto.getCategory());
	        sweet.setPrice(dto.getPrice());
	        sweet.setQuantity(dto.getQuantity());
	        return sweet;
	    }

	    // CREATE sweet (Admin only)
	    @PostMapping
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> create(@Valid @RequestBody SweetDto dto) {
	        Sweet sweet = sweetService.create(toEntity(dto));
	        return ResponseEntity.ok(toDto(sweet));
	    }

	    // LIST sweets
	    @GetMapping
	    public ResponseEntity<?> list(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "20") int size
	    ) {
	        return ResponseEntity.ok(sweetService.list(page, size).map(this::toDto));
	    }

	    // SEARCH sweets
	    @GetMapping("/search")
	    public ResponseEntity<?> search(
	            @RequestParam(required = false) String name,
	            @RequestParam(required = false) String category,
	            @RequestParam(required = false) BigDecimal minPrice,
	            @RequestParam(required = false) BigDecimal maxPrice,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "20") int size
	    ) {
	        if (name != null)
	            return ResponseEntity.ok(sweetService.searchByName(name, page, size).map(this::toDto));

	        if (category != null)
	            return ResponseEntity.ok(
	                    sweetService.searchByName(category, page, size).map(this::toDto)
	            );

	        if (minPrice != null && maxPrice != null)
	            return ResponseEntity.ok(
	                    sweetService.searchByPrice(minPrice, maxPrice, page, size).map(this::toDto)
	            );

	        return ResponseEntity.badRequest().body("Invalid search parameters");
	    }

	    // PURCHASE sweet
//	    @PostMapping("/{id}/purchase")
//	    public ResponseEntity<?> purchase(
//	            @PathVariable Integer id,
//	            @Valid @RequestBody PurchaseRequest request
//	    ) {
//	        Sweet sweet = sweetService.purchase(id, request.getQuantity());
//	        return ResponseEntity.ok(toDto(sweet));
//	    }
	    
	    @PostMapping("/{id}/purchase")
	    public ResponseEntity<?> purchase(
	            @PathVariable int id,
	            @RequestBody PurchaseRequest request) {

	        sweetService.purchase(id, request.getQuantity());
	        return ResponseEntity.ok().build();
	    }


	    // RESTOCK sweet (Admin only)
	    @PostMapping("/{id}/restock")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> restock(
	            @PathVariable Integer id,
	            @Valid @RequestBody PurchaseRequest request
	    ) {
	        Sweet sweet = sweetService.restock(id, request.getQuantity());
	        return ResponseEntity.ok(toDto(sweet));
	    }

	    // UPDATE sweet (Admin only)
	    @PutMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> update(
	            @PathVariable Integer id,
	            @Valid @RequestBody SweetDto dto
	    ) {
	        Sweet updated = sweetService.update(id, toEntity(dto));
	        return ResponseEntity.ok(toDto(updated));
	    }

	    // DELETE sweet (Admin only)
	    @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> delete(@PathVariable Integer id) {
	        sweetService.delete(id);
	        return ResponseEntity.noContent().build();
	    }
	}

