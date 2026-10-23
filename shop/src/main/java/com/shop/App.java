package com.shop;

import com.shop.Models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(considerNestedRepositories = true)
public class App {
  public static void main(String[] args) { SpringApplication.run(App.class, args); }

  @Bean CommandLineRunner seed(Repos.Users users, Repos.Products products) {
    return a -> {
      var enc = new BCryptPasswordEncoder();
      if (users.count() == 0) {
        mk(users, enc, "admin", "admin123", "Quản trị viên", "ADMIN");
        mk(users, enc, "nhanvien", "nv123456", "Nhân viên bán hàng", "STAFF");
        mk(users, enc, "khach", "khach123", "Khách hàng mẫu", "CUSTOMER");
      }
      if (products.count() == 0) {
        p(products, "iPhone 15", "Điện thoại", "📱", 19990000, 20);
        p(products, "Samsung Galaxy S24", "Điện thoại", "📱", 17990000, 15);
        p(products, "Laptop Dell XPS 13", "Laptop", "💻", 29990000, 8);
        p(products, "MacBook Air M2", "Laptop", "💻", 26990000, 10);
        p(products, "Tai nghe Sony WH-1000XM5", "Phụ kiện", "🎧", 6990000, 30);
        p(products, "Chuột Logitech MX Master 3", "Phụ kiện", "🖱️", 2290000, 40);
      }
    };
  }

  static void mk(Repos.Users r, BCryptPasswordEncoder e, String u, String pw, String n, String role) {
    var x = new User(); x.username = u; x.password = e.encode(pw); x.fullName = n; x.role = role; r.save(x);
  }
  static void p(Repos.Products r, String n, String c, String img, long price, int stock) {
    var x = new Product(); x.name = n; x.category = c; x.image = img; x.price = price; x.stock = stock; r.save(x);
  }
}
