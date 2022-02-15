package dev.codescreen.marketplace.service;

import dev.codescreen.marketplace.dto.StoreDto;
import dev.codescreen.marketplace.model.Store;
import dev.codescreen.marketplace.repository.StoreRepository;
import dev.codescreen.marketplace.service.impl.InMemoryStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InMemoryStoreServiceTest {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    InMemoryStoreService storeService;

    @Test
    public void shouldReturnExistStore() {
        Store store = new Store();
        store.setName("Store name");
        store.setEmail("store@markeplace.com");
        store.setPhone("+123456789");
        storeRepository.save(store);

        Optional<StoreDto> opt = storeService.getStore(store.getId());

        assertTrue(opt.isPresent());

        opt.ifPresent(storeDto -> {
            assertNotNull("Store shouldn't be null", storeDto);
            assertEquals("Store name", storeDto.getName());
            assertEquals("store@markeplace.com", storeDto.getEmail());
            assertEquals("+123456789", storeDto.getPhone());
        });
    }

    @Test
    public void shouldReturnEmptyForNotExistingStore() {
        assertFalse(storeService.getStore(999L).isPresent());
    }
}
