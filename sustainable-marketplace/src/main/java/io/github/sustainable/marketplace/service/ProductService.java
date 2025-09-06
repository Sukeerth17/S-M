package io.github.sustainable.marketplace.service;

import io.github.sustainable.marketplace.dto.request.ProductCreateRequest;
import io.github.sustainable.marketplace.dto.request.ProductUpdateRequest;
import io.github.sustainable.marketplace.dto.response.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductCreateRequest request, UUID userId);
    ProductDto updateProduct(UUID productId, ProductUpdateRequest request);
    void deleteProduct(UUID productId);
    ProductDto getProductById(UUID productId);
    List<ProductDto> getProductsByCategory(UUID categoryId);
    List<ProductDto> getAllProducts();
}