package dev.codescreen.marketplace.controller;

import com.google.common.collect.ImmutableList;
import dev.codescreen.marketplace.dto.ProductDto;
import dev.codescreen.marketplace.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    ProductService productService;

    private ProductDto createProduct(String name, Long storeId, double price) {
        ProductDto product = new ProductDto();
        product.setName(name);
        product.setStoreId(storeId);
        product.setPrice(BigDecimal.valueOf(price).setScale(2, RoundingMode.CEILING));
        return product;
    }

    @Before
    public void setUp() {
        Mockito.reset(productService);
    }

    @Test
    public void shouldAddProduct() throws Exception {
        String json = "{ \"name\":\"Book\", \"price\":11.99 }";

        ProductDto product = new ProductDto();
        product.setName("Book");
        product.setPrice(BigDecimal.valueOf(11.99).setScale(2, RoundingMode.CEILING));

        ProductDto createdProduct = new ProductDto();
        product.setName("Book");
        product.setPrice(BigDecimal.valueOf(11.99).setScale(2, RoundingMode.CEILING));
        product.setStoreId(1L);
        product.setId(1L);

        when(productService.addProduct(product, 1L)).thenReturn(createdProduct);

        mockMvc.perform(post("/store/1/product").accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnProductById() throws Exception {
        ProductDto product = createProduct("Book", 10L, 19.99);

        when(productService.getProduct(10L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/product/10").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Book")))
                .andExpect(jsonPath("$.storeId", is(10)))
                .andExpect(jsonPath("$.price", is(19.99)));
    }

    @Test
    public void shouldNotReturnProductById() throws Exception {
        when(productService.getProduct(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/product/1").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnProductsByStore() throws Exception {

        ProductDto product1 = createProduct("Book", 1L, 21.55);
        ProductDto product2 = createProduct("Pen", 1L, 12.66);
        List<ProductDto> products = ImmutableList.<ProductDto>builder()
                .add(product1)
                .add(product2)
                .build();

        when(productService.findProductsByStoreId(1L)).thenReturn(products);

        mockMvc.perform(get("/store/1/product").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(products.size()))
                .andExpect(jsonPath("$[0].name", is("Book")))
                .andExpect(jsonPath("$[0].storeId", is(1)))
                .andExpect(jsonPath("$[0].price", is(21.55)))
                .andExpect(jsonPath("$[1].name", is("Pen")))
                .andExpect(jsonPath("$[1].storeId", is(1)))
                .andExpect(jsonPath("$[1].price", is(12.66)));
    }

    @Test
    public void shouldNotReturnProductsByStore() throws Exception {
        when(productService.findProductsByStoreId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/store/1/product").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnProductsByName() throws Exception {
        ProductDto product1 = createProduct("Book", 1L, 21.55);
        ProductDto product2 = createProduct("Book", 2L, 12.66);
        ProductDto product3 = createProduct("Book", 4L, 20.50);

        List<ProductDto> products = ImmutableList.<ProductDto>builder()
                .add(product1)
                .add(product2)
                .add(product3)
                .build();

        when(productService.findProductsByName("Book")).thenReturn(products);

        mockMvc.perform(get("/product/find/Book").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(products.size()))
                .andExpect(jsonPath("$[0].name", is("Book")))
                .andExpect(jsonPath("$[0].storeId", is(1)))
                .andExpect(jsonPath("$[0].price", is(21.55)))
                .andExpect(jsonPath("$[1].name", is("Book")))
                .andExpect(jsonPath("$[1].storeId", is(2)))
                .andExpect(jsonPath("$[1].price", is(12.66)))
                .andExpect(jsonPath("$[2].name", is("Book")))
                .andExpect(jsonPath("$[2].storeId", is(4)))
                .andExpect(jsonPath("$[2].price", is(20.50)));
    }

    @Test
    public void shouldNotReturnProductsByName() throws Exception {
        when(productService.findProductsByName("Book")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/product/find/Book").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
