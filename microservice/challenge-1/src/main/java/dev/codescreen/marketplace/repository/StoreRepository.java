package dev.codescreen.marketplace.repository;

import dev.codescreen.marketplace.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByName(String name);
}
