package com.snd.storefinder.controllers;

import com.snd.storefinder.dtos.StoreCreationRequest;
import com.snd.storefinder.dtos.StoreInfoResponse;
import com.snd.storefinder.dtos.product.StoreProductInfo;
import com.snd.storefinder.enums.UserRole;
import com.snd.storefinder.models.User;
import com.snd.storefinder.models.store.StoreManagerId;
import com.snd.storefinder.services.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/")
    public ResponseEntity<StoreInfoResponse> createNewStore(@RequestBody StoreCreationRequest request,
                                                            @AuthenticationPrincipal User user) {
        StoreInfoResponse response = storeService.createStore(request, user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/manager")
    public ResponseEntity<StoreManagerId> addStoreManager(Integer storeId, Integer managerId,
                                                          UserRole userRole,
                                                          @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(storeService.addStoreAdmin(storeId, managerId, userRole, user), HttpStatus.OK);
    }

    @DeleteMapping("/manager")
    public ResponseEntity<Void> deleteStoreManager(Integer storeId, Integer managerId,
                                                   @AuthenticationPrincipal User user) {
        storeService.removeStoreAdmin(storeId, managerId, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/product")
    public ResponseEntity<StoreProductInfo> addProductToStore(@RequestParam Integer productId,
                                                              @RequestParam Integer storeId,
                                                              @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(storeService.addProductToStore(productId, storeId, user), HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<StoreProductInfo> getProductsFromStore(@RequestParam Integer productId,
                                                                 @RequestParam Integer storeId,
                                                                 @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(storeService.getStoreProductInfo(storeId, productId, user));
    }



}
