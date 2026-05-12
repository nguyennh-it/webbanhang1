package com.example.demo.service;

import com.example.demo.UserRepository.*;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    public void addToCart(String productId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(1)
                    .build();
            cartItemRepository.save(newItem);
        }
    }
    public void removeFromCart(Long id) {
        cartItemRepository.deleteById(id);
    }
    public List<CartItem> getCartItems(String username) {
        return cartItemRepository.findByUserUsername(username);
    }
    @org.springframework.transaction.annotation.Transactional
    public void checkout(String username) {
        // 1. Tìm thằng User đang đăng nhập và lấy giỏ hàng của nó
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        List<CartItem> cartItems = cartItemRepository.findByUserUsername(username);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng đang trống, không thể thanh toán!");
        }

        // 2. Tạo hóa đơn mới (Order)
        // Lưu ý: Dùng đường dẫn đầy đủ nếu IntelliJ vẫn báo đỏ class Order
        com.example.demo.entity.Order order = new com.example.demo.entity.Order();
        order.setId(java.util.UUID.randomUUID().toString()); // Tạo ID ngẫu nhiên vì ID là String
        order.setUser(user);
        order.setStatus("PENDING");
        order.setCreated_at(new java.sql.Timestamp(System.currentTimeMillis()));

        // Tính tổng tiền đơn hàng
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        order.setTotal_price(total);

        // Lưu Order xuống database trước để lấy làm khóa ngoại cho OrderDetail
        orderRepository.save(order);

        // 3. Chuyển từng món từ Giỏ hàng sang Chi tiết hóa đơn (OrderDetail)
        for (CartItem item : cartItems) {
            com.example.demo.entity.OrderDetail detail = new com.example.demo.entity.OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getProduct().getPrice()); // Lưu giá lúc mua
            orderDetailRepository.save(detail);
        }

        // 4. Thanh toán xong thì xóa sạch giỏ hàng của thằng này đi
        cartItemRepository.deleteAll(cartItems);
    }
}
