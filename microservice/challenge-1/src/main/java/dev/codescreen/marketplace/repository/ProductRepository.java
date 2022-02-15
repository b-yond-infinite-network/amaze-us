package dev.codescreen.marketplace.repository;

import dev.codescreen.marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);

    List<Product> findByStoreId(Long storeId);
}
