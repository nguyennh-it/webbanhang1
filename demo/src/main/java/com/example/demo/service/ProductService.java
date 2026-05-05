package com.example.demo.service;

import com.example.demo.UserRepository.ProductRepository;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.ProductMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    public ProductResponse  createProduct(ProductRequest request) {
        if (productRepository.existsByName(request.getName())) {

            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }
        Product product=productMapper.toProduct(request);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toProductResponse)
                .toList();
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        productMapper.updateProduct(product, request);

        return productMapper.toProductResponse(productRepository.save(product));
    }
    public ProductResponse getProduct(String id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
    }
    public List<ProductResponse> searchProducts(String keyword) {
        // Gọi repository để tìm danh sách product theo tên
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(productMapper::toProductResponse) // Chuyển đổi sang DTO nếu bạn đang dùng MapStruct
                .toList();
    }
}