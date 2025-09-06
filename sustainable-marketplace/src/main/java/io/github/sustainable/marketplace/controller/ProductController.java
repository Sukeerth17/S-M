package io.github.sustainable.marketplace.controller;

import io.github.sustainable.marketplace.dto.request.ProductCreateRequest;
import io.github.sustainable.marketplace.dto.request.ProductUpdateRequest;
import io.github.sustainable.marketplace.dto.response.ProductDto;
import io.github.sustainable.marketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
//@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ProductDto> createProduct(
            @PathVariable UUID userId,
            @RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok(productService.createProduct(request, userId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable UUID productId,
            @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}