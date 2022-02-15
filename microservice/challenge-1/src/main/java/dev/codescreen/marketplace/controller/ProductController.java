package dev.codescreen.marketplace.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.codescreen.marketplace.dto.ProductDto;
import dev.codescreen.marketplace.service.ProductService;

@Controller
@RestController
public class ProductController {

	private ProductService productService;
	
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

	@GetMapping(value= "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDto> findProductbyId(@PathVariable("id") Long id) {
        
    	Optional<ProductDto> product = this.productService.getProduct(id);
        
    	if (product.isPresent()) {
    		return ResponseEntity.ok(product.get());    		
    	}
    	
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    }
	
	@GetMapping(value= "/store/{storeId}/product", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDto>> findProductbyStoreId(@PathVariable("storeId") Long storeId) {
        
    	List<ProductDto> products = this.productService.findProductsByStoreId(storeId);
        
    	if (!products.isEmpty()) {
    		return ResponseEntity.ok(products);    		
    	}
    	
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
	
	
	@PostMapping(value= "/store/{storeId}/product", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> findProductbyName(@PathVariable("storeId") Long storeId, @RequestBody ProductDto productRequest) {
        
    	ProductDto productDto = this.productService.addProduct(productRequest, storeId);
        
    	return new ResponseEntity<>(productDto, HttpStatus.CREATED);    		
    	

    }
	
	@GetMapping(value= "/product/find/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDto>> findProductbyName(@PathVariable("name") String name) {
        
    	List<ProductDto> products = this.productService.findProductsByName(name);
        
    	if (!products.isEmpty()) {
    		return ResponseEntity.ok(products);    		
    	}
    	
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
