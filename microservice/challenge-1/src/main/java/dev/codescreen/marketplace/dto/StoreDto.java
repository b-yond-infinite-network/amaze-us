package dev.codescreen.marketplace.dto;

import com.opengamma.strata.collect.ArgChecker;
import dev.codescreen.marketplace.model.Store;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public class StoreDto implements Serializable {

    private Long id;

    private String name;

    private String phone;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Store toEntity() {
        Store store = new Store();
        BeanUtils.copyProperties(this, store);
        return store;
    }

    public static StoreDto of(Store store) {
        ArgChecker.notNull(store, "store");
        StoreDto dto = new StoreDto();
        BeanUtils.copyProperties(store, dto);
        return dto;
    }
}
