package com.example.demo.service;

import com.example.demo.UserRepository.CartRepository;
import com.example.demo.UserRepository.ProductRepository;
import com.example.demo.entity.CartItem;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    ProductRepository productRepository;
    public List<CartItem> getCartItems(String userId) {
        return cartRepository.findByUserId(userId);
    }
    public void addToCart(String productId) {
        // 1. Tìm sản phẩm trong DB
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        // Kiểm tra xem sản phẩm này đã có trong giỏ của user123 chưa
        var existingItem = cartRepository.findByUserId("user123").stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if (existingItem.isPresent()) {
            // Nếu có rồi thì tăng số lượng lên 1
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            cartRepository.save(item);
        } else {
            // Nếu chưa có thì mới tạo mới như code cũ của bạn
            CartItem item = CartItem.builder()
                    .product(product)
                    .quantity(1)
                    .userId("user123")
                    .build();
            cartRepository.save(item);
        }
    }
}