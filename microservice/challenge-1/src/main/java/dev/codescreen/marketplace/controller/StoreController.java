package dev.codescreen.marketplace.controller;

import dev.codescreen.marketplace.controller.exception.ResourceNotFoundException;
import dev.codescreen.marketplace.dto.StoreDto;
import dev.codescreen.marketplace.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/store")
public class StoreController {

    private StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping(value = "/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreDto getStore(@PathVariable Long storeId) {
        return storeService.getStore(storeId)
                           .orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoreDto createStore(@RequestBody StoreDto storeDto) {
        return storeService.createStore(storeDto);
    }

    @GetMapping(value = "/find/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<StoreDto> findStoreByName(@PathVariable String name) {
        List<StoreDto> stores = storeService.findStoreByName(name);
        if (stores == null || stores.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return stores;
    }

}
