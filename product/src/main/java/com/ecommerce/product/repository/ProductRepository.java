package com.ecommerce.product.repository;

import com.ecommerce.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByisActiveTrue();

    @Query("SELECT p FROM products p WHERE p.isActive = true AND p.productQuantity > 0 AND LOWER(p.productName) LIKE LOWER(CONCAT('%',:keyWord, '%'))")
    List<Product> searchProducts(@Param("keyWord") String keyWord);

    Optional<Product> findByIdAndIsActiveTrue(long id);
}
