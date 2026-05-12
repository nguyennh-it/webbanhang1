package com.example.demo.UserRepository;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Thêm cái này cho chuẩn
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> { // Sửa String thành Long
    CartItem findByUserAndProduct(User user, Product product);
    List<CartItem> findByUserUsername(String username);
}