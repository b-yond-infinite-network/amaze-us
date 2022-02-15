package dev.codescreen.marketplace.service;

import dev.codescreen.marketplace.dto.ProductDto;
import dev.codescreen.marketplace.dto.StoreDto;
import dev.codescreen.marketplace.model.Product;
import dev.codescreen.marketplace.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InMemoryProductServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    StoreService storeService;

    private Product createProduct(long storeId, String name, double price) {
        Product product = new Product();
        product.setStoreId(storeId);
        product.setName(name);
        product.setPrice(BigDecimal.valueOf(price));
        return product;
    }

    @Test
    public void shouldAddProduct() {

        StoreDto storeDto = new StoreDto();
        storeDto.setName("Test store");
        storeDto.setEmail("test@store.com");
        storeDto.setPhone("+123456789");

        StoreDto createdStore = storeService.createStore(storeDto);

        ProductDto productDto = new ProductDto();
        productDto.setName("Product 1");
        productDto.setPrice(BigDecimal.valueOf(12.34));

        ProductDto createdProduct = productService.addProduct(productDto, createdStore.getId());
        assertNotNull(createdProduct);

        Optional<Product> opt = productRepository.findById(createdProduct.getId());

        assertTrue(opt.isPresent());

        Product product = opt.get();

        assertNotNull(product);
        assertEquals("Product 1", product.getName());
        assertEquals(BigDecimal.valueOf(12.34), product.getPrice());
    }

    @Test
    public void shouldReturnAddedProduct() {

        Product product = createProduct(1L, "Product 1", 12.34);
        Product createdProduct = productRepository.save(product);

        Optional<ProductDto> opt = productService.getProduct(createdProduct.getId());
        assertTrue(opt.isPresent());

        ProductDto productDto = opt.get();

        assertNotNull(productDto);
        assertEquals("Product 1", productDto.getName());
        assertEquals(BigDecimal.valueOf(12.34), productDto.getPrice());
    }

    @Test
    public void shouldReturnPriceOrderedListOfProductsByName() {
        productRepository.save(createProduct(1L, "Book", 12.34));
        productRepository.save(createProduct(1L, "Pen", 10.00));
        productRepository.save(createProduct(2L, "Book", 11.50));

        List<ProductDto> products = productService.findProductsByName("Book");

        assertEquals(2, products.size());

        assertEquals("Book", products.get(0).getName());
        assertEquals(BigDecimal.valueOf(11.50).setScale(2, RoundingMode.CEILING), products.get(0).getPrice());
        assertEquals(Long.valueOf(2L), products.get(0).getStoreId());
        assertEquals("Book", products.get(1).getName());
        assertEquals(BigDecimal.valueOf(12.34).setScale(2, RoundingMode.CEILING), products.get(1).getPrice());
        assertEquals(Long.valueOf(1L), products.get(1).getStoreId());
    }

    @Test
    public void shouldReturnEmptyList() {
        List<ProductDto> products = productService.findProductsByName("NotExistingProduct");
        assertEquals(0, products.size());
    }

    @Test
    public void shouldReturnProductsByStoreId() {
        productRepository.save(createProduct(4L, "Book", 12.34));
        productRepository.save(createProduct(4L, "Pen", 10.00));
        productRepository.save(createProduct(4L, "Eraser", 11.50));

        List<ProductDto> products = productService.findProductsByStoreId(4L);

        assertEquals(3, products.size());

        assertEquals("Book", products.get(0).getName());
        assertEquals(BigDecimal.valueOf(12.34).setScale(2, RoundingMode.CEILING), products.get(0).getPrice());
        assertEquals(Long.valueOf(4L), products.get(0).getStoreId());
        assertEquals("Pen", products.get(1).getName());
        assertEquals(BigDecimal.valueOf(10.00).setScale(2, RoundingMode.CEILING), products.get(1).getPrice());
        assertEquals(Long.valueOf(4L), products.get(1).getStoreId());
        assertEquals("Eraser", products.get(2).getName());
        assertEquals(BigDecimal.valueOf(11.50).setScale(2, RoundingMode.CEILING), products.get(2).getPrice());
        assertEquals(Long.valueOf(4L), products.get(2).getStoreId());
    }

    @Test
    public void shouldNotReturnProductsByStoreId() {
        List<ProductDto> products = productService.findProductsByStoreId(5L);
        assertEquals(0, products.size());
    }
}
