package com.example.demo.configuration;

import com.example.demo.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration                                                  //Đánh dấu đây là một file cấu hình
@EnableWebSecurity                                                  //Kích hoạt tính năng bảo mật web của Spring Security
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {                 //Quy định thuật toán mã hóa mật khẩu
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {          //Là trung tâm điều khiển việc xác thực.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationManager(authenticationManager())
            .authorizeHttpRequests(auth -> auth
                // Trang login và tài nguyên tĩnh: ai cũng vào được
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll() //Bất kỳ ai, kể cả khách chưa đăng nhập, đều có quyền truy cập
                // Xem danh sách: ai cũng xem được
                .requestMatchers("/store/products").permitAll()
                // Thêm/sửa/xóa: chỉ ADMIN
                .requestMatchers("/store/add/**", "/store/edit/**", "/store/delete/**").hasRole("ADMIN")
                // Còn lại phải đăng nhập
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")   // URL nhận POST từ form
                .usernameParameter("username")  // khớp với name="username" trong HTML
                .passwordParameter("password")  // khớp với name="password" trong HTML
                .defaultSuccessUrl("/store/products", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .exceptionHandling(ex -> ex.accessDeniedPage("/403"))
            .csrf(csrf -> csrf.disable()); // tắt CSRF để form hoạt động đơn giản

        return http.build();
    }
}
