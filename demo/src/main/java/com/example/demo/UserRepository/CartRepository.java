package com.example.demo.UserRepository;

import com.example.demo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem,String> {

    List<CartItem> findByUserId(String userId);
}
