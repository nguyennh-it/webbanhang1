package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // Trang báo lỗi khi không đủ quyền
    }
}
