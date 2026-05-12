package com.example.demo.controller;

import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public String viewCart(Model model, Authentication auth) {
        model.addAttribute("items", cartService.getCartItems(auth.getName()));
        return "cart";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable String id, Authentication auth) {
        cartService.addToCart(id, auth.getName());
        return "redirect:/products";
    }
}