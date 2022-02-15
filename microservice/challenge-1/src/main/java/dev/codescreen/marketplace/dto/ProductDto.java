package dev.codescreen.marketplace.dto;

import com.opengamma.strata.collect.ArgChecker;
import dev.codescreen.marketplace.model.Product;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductDto implements Serializable {

    private Long id;

    private Long storeId;

    private String name;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product toEntity() {
        Product product = new Product();
        BeanUtils.copyProperties(this, product);
        return product;
    }

    public static ProductDto of(Product product) {
        ArgChecker.notNull(product, "product");
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

	public ProductDto(String name, BigDecimal price) {
		super();
		this.name = name;
		this.price = price;
	}
    

	public ProductDto() {
		super();
	}
    
}
