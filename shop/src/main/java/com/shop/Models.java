package com.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

public class Models {
  @Entity(name = "User") @Table(name = "users")
  public static class User {
    @Id @GeneratedValue public Long id;
    @Column(unique = true) public String username;
    @JsonIgnore public String password;
    public String fullName;
    public String role = "CUSTOMER"; // ADMIN | STAFF | CUSTOMER
    public boolean locked;
  }

  @Entity(name = "Product") @Table(name = "products", indexes = @Index(columnList = "category"))
  public static class Product {
    @Id @GeneratedValue public Long id;
    public String name, category, image;
    public long price;
    public int stock;
  }

  @Entity(name = "Order") @Table(name = "orders")
  public static class Order {
    @Id @GeneratedValue public Long id;
    public Long userId;
    public String customerName, phone, address, payment;
    public String status = "PENDING"; // PENDING | SHIPPING | DELIVERED | CANCELLED
    public long total;
    public LocalDateTime createdAt = LocalDateTime.now();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) @JoinColumn(name = "order_id")
    public List<Item> items = new ArrayList<>();
  }

  @Entity(name = "Item") @Table(name = "order_items")
  public static class Item {
    @Id @GeneratedValue public Long id;
    public Long productId;
    public String productName;
    public long price;
    public int qty;
  }
}
