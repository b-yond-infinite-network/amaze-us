package dev.codescreen.marketplace.service;

import dev.codescreen.marketplace.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductDto addProduct(ProductDto product, Long storeId);

    Optional<ProductDto> getProduct(Long productId);

    List<ProductDto> findProductsByName(String productName);

    List<ProductDto> findProductsByStoreId(Long storeId);
}
