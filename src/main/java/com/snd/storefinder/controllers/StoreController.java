package com.snd.storefinder.controllers;

import com.snd.storefinder.dtos.StoreCreationRequest;
import com.snd.storefinder.dtos.StoreInfoResponse;
import com.snd.storefinder.dtos.product.StoreProductInfo;
import com.snd.storefinder.enums.UserRole;
import com.snd.storefinder.models.User;
import com.snd.storefinder.models.store.StoreManagerId;
import com.snd.storefinder.models.store.StoreProduct;
import com.snd.storefinder.services.StoreService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<StoreManagerId> addStoreManager(@RequestParam Integer store,
                                                          @RequestParam Integer manager,
                                                          @RequestParam UserRole role,
                                                          @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(storeService.addStoreAdmin(store, manager, role, user), HttpStatus.OK);
    }

    @DeleteMapping("/manager")
    public ResponseEntity<Void> deleteStoreManager(@RequestParam Integer store,
                                                   @RequestParam Integer manager,
                                                   @AuthenticationPrincipal User user) {
        storeService.removeStoreAdmin(store, manager, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/product")
    public ResponseEntity<StoreProductInfo> addProductToStore(@RequestParam Integer product,
                                                              @RequestParam Integer store,
                                                              @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(storeService.addProductToStore(product, store, user), HttpStatus.OK);
    }

    @PatchMapping("product/stock")
    public ResponseEntity<StoreProductInfo> updateProductStockInStore(@RequestParam Integer product,
                                                                 @RequestParam Integer store,
                                                                 @RequestParam Integer stock,
                                                                 @AuthenticationPrincipal User user) {
       StoreProductInfo info = storeService.updateProductStock(product, store, stock, user);
       return new ResponseEntity<>(info, HttpStatus.OK);
    }


    @PatchMapping("/product/price")
    public ResponseEntity<StoreProductInfo> updateProductPriceInStore(@RequestParam Integer product,
                                                                      @RequestParam Integer store,
                                                                      @RequestParam Double price,
                                                                      @AuthenticationPrincipal User user) {
        StoreProductInfo info = storeService.updateProductPrice(product, store, price, user);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @DeleteMapping("/product")
    public ResponseEntity<Void> deleteProductFromStore(@RequestParam Integer product,
                                                       @RequestParam Integer store,
                                                       @AuthenticationPrincipal User user) {
        storeService.removeProductFromStore(product, store, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/product")
    public ResponseEntity<StoreProductInfo> getProductsFromStore(@RequestParam Integer product,
                                                                 @RequestParam Integer store,
                                                                 @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(storeService.getStoreProductInfo(store, product, user));
    }

    @GetMapping("{id}/products")
    public ResponseEntity<List<StoreProductInfo>> getAllProductsFromStore(@PathVariable("id") Integer id,
                                                                          @RequestParam("size") Integer size,
                                                                          @RequestParam("page") Integer page,
                                                                          @AuthenticationPrincipal User user) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<StoreProductInfo> list = storeService.getAllProductsOfStore(id, user, pageRequest);
        return ResponseEntity.ok(list);
    }

}
