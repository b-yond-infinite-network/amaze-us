package dev.codescreen.marketplace.service;

import dev.codescreen.marketplace.dto.StoreDto;

import java.util.List;
import java.util.Optional;

public interface StoreService {

    StoreDto createStore(StoreDto store);

    Optional<StoreDto> getStore(Long storeId);

    List<StoreDto> findStoreByName(String storeName);
}
