package com.example.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @NotBlank(message = "PRODUCT_NAME_REQUIRED")
    String name;
    String description;
    @Min(value = 0, message = "INVALID_PRICE")
    double price;
    int stock;
    String category;
    String imageUrl;
}
