package com.example.demo.Controller;

import com.example.demo.entity.CartItem;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private  final CartService cartService;
    @PostMapping("/add")
    public String  addToCart(@RequestParam String productId){
    cartService.addToCart(productId);
    return "redirect:/store/products"; //them xong quay lại trang
    }
    @GetMapping("/view")
    public String viewCart(Model model) {
        // Lấy danh sách từ Service (Bạn có thể thay "user123" bằng logic lấy user hiện tại)
        List<CartItem> cartItems = cartService.getCartItems("user123");
        model.addAttribute("cartItems", cartItems);

        // Tính tổng tiền
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("totalPrice", total);

        return "cart"; // Trả về file cart.html
    }
}
