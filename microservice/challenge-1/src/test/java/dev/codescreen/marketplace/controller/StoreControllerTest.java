package dev.codescreen.marketplace.controller;

import com.google.common.collect.ImmutableList;
import dev.codescreen.marketplace.dto.StoreDto;
import dev.codescreen.marketplace.service.StoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    StoreService storeService;

    private StoreDto createStore(String name, String email, String phone) {
        StoreDto store = new StoreDto();
        store.setName(name);
        store.setEmail(email);
        store.setPhone(phone);
        return store;
    }

    @Before
    public void setUp() {
        Mockito.reset(storeService);
    }

    @Test
    public void shouldReturnStoreById() throws Exception {
        StoreDto store = createStore("Test store", "store@marketplace.com", "+123456789");

        when(storeService.getStore(1L)).thenReturn(Optional.of(store));

        mockMvc.perform(get("/store/1").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test store")))
                .andExpect(jsonPath("$.email", is("store@marketplace.com")))
                .andExpect(jsonPath("$.phone", is("+123456789")));
    }

    @Test
    public void shouldNotReturnStoreById() throws Exception {
        when(storeService.getStore(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/store/1").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnFoundStoreByName() throws Exception {
        StoreDto store = createStore("Bookstore", "books@marketplace.com", "+123456789");
        List<StoreDto> stores = ImmutableList.of(store);

        when(storeService.findStoreByName("Bookstore")).thenReturn(stores);

        mockMvc.perform(get("/store/find/Bookstore").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("length()").value(stores.size()))
                .andExpect(jsonPath("$[0].name", is("Bookstore")))
                .andExpect(jsonPath("$[0].email", is("books@marketplace.com")))
                .andExpect(jsonPath("$[0].phone", is("+123456789")));
    }

    @Test
    public void shouldNotReturnStoreByName() throws Exception {
        when(storeService.findStoreByName("IncorrectStoreName")).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/store/find/IncorrectStoreName").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
