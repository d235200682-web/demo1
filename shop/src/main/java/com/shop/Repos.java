package com.shop;

import com.shop.Models.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface Repos {
  interface Users extends JpaRepository<User, Long> { Optional<User> findByUsername(String u); }

  interface Products extends JpaRepository<Product, Long> {
    @Query("select p from Product p where (:c is null or p.category=:c) and (:q is null or lower(p.name) like lower(concat('%',:q,'%'))) order by p.id desc")
    List<Product> search(@Param("c") String c, @Param("q") String q);
    @Query("select distinct p.category from Product p order by p.category")
    List<String> categories();
  }

  interface Orders extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByIdDesc(Long uid);
    List<Order> findAllByOrderByIdDesc();
  }
}
