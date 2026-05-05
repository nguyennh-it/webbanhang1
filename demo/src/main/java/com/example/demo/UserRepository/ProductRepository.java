package com.example.demo.UserRepository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    boolean existsByName(String name);
    List<Product> findByNameContainingIgnoreCase(String name);
}
