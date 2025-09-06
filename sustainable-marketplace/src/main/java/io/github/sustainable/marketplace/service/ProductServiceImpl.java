package io.github.sustainable.marketplace.service;

import io.github.sustainable.marketplace.dto.request.ProductCreateRequest;
import io.github.sustainable.marketplace.dto.request.ProductUpdateRequest;
import io.github.sustainable.marketplace.dto.response.ProductDto;
import io.github.sustainable.marketplace.entity.Category;
import io.github.sustainable.marketplace.entity.Product;
import io.github.sustainable.marketplace.entity.User;
import io.github.sustainable.marketplace.repository.CategoryRepository;
import io.github.sustainable.marketplace.repository.ProductRepository;
import io.github.sustainable.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto createProduct(ProductCreateRequest request, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();

        Product product = new Product();
        product.setUser(user);
        product.setCategory(category);
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());

        return mapToDto(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(UUID productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());

        return mapToDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        return productRepository.findById(productId)
                .map(this::mapToDto)
                .orElseThrow();
    }

    @Override
    public List<ProductDto> getProductsByCategory(UUID categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategoryId(product.getCategory().getCategoryId());
        dto.setUserId(product.getUser().getUserId());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}