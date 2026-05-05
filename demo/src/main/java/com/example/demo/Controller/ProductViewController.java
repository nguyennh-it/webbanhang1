package com.example.demo.Controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductViewController {

    ProductService productService;

    // Hiển thị danh sách sản phẩm
    @GetMapping("/products")
    public String listProducts(
            Model model,
            @RequestParam(name = "keyword", required = false) String keyword) {

        // Nếu có keyword thì tìm kiếm, không thì lấy tất cả
        var products = (keyword != null && !keyword.isEmpty())
                ? productService.searchProducts(keyword)
                : productService.getAllProducts();

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword); // Gửi lại keyword để giữ chữ trong ô nhập
        return "product-list";
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/add")
    public String showAddForm() {
        return "add-product"; // → templates/add-product.html
    }

    // Xử lý thêm sản phẩm
    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductRequest request) {
        productService.createProduct(request);
        return "redirect:/store/products";
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "edit-product"; // → templates/edit-product.html
    }

    // Xử lý cập nhật sản phẩm
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable String id, @ModelAttribute ProductRequest request) {
        productService.updateProduct(id, request);
        return "redirect:/store/products";
    }

    // Xử lý xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "redirect:/store/products";
    }

    // Hiển thị danh sách sản phẩm (Có hỗ trợ tìm kiếm)

}