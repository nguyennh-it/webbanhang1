package com.example.demo.Controller;

import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // Sửa chỗ này để khớp với cái URL mày hay gõ
    @GetMapping("/view")
    public String viewCart(Model model, Authentication auth) {
        if (auth == null) return "redirect:/login";

        // Lưu ý: "items" này phải khớp với th:each="item : ${items}" trong file cart.html
        model.addAttribute("items", cartService.getCartItems(auth.getName()));
        return "cart";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable String id, Authentication auth) {
        if (auth == null) return "redirect:/login";

        cartService.addToCart(id, auth.getName());

        // Sửa lại redirect cho đúng cái trang cửa hàng của mày
        return "redirect:/store/products";
    }
}