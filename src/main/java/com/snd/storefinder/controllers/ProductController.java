package com.snd.storefinder.controllers;

import com.snd.storefinder.dtos.Location;
import com.snd.storefinder.dtos.product.ProductCreationRequest;
import com.snd.storefinder.dtos.product.ProductInfoResponse;
import com.snd.storefinder.dtos.product.StoreProductInfo;
import com.snd.storefinder.models.User;
import com.snd.storefinder.models.store.Product;
import com.snd.storefinder.services.ProductService;
import com.snd.storefinder.services.StoreService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final StoreService storeService;


    public ProductController(ProductService productService, StoreService storeService) {
        this.productService = productService;
        this.storeService = storeService;
    }


    @PostMapping("/")
    public ResponseEntity<ProductInfoResponse> createNewProduct(@RequestBody ProductCreationRequest product) {
        ProductInfoResponse info = productService.createNewProduct(product);
        return ResponseEntity.ok(info);
    }

    @GetMapping("/")
    public ResponseEntity<ProductInfoResponse> getProductInfo(@RequestParam("id") Integer productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(productService.getProductInfoById(productId));
    }

    @GetMapping("/stores/")
    public ResponseEntity<List<StoreProductInfo>> getAllStoresContainingProduct(@RequestParam("id") Integer productId,
                                                                                @RequestParam("size") Integer size,
                                                                                @RequestParam("page") Integer page) {
        PageRequest req = PageRequest.of(page, size);
        List<StoreProductInfo> list = productService.findAllStoreContainingProduct(productId, req);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/stores/near")
    public ResponseEntity<List<StoreProductInfo>> getAllStoreContainingProductsNearMe(@RequestParam("id") Integer id,
                                                                                      @RequestBody Location location,
                                                                                      @RequestParam("distance") Double distance,
                                                                                      @RequestParam("size") Integer size,
                                                                                      @RequestParam("page") Integer page,
                                                                                      @AuthenticationPrincipal User user) {
        PageRequest req = PageRequest.of(page, size);
        List<StoreProductInfo> list = storeService.getAllStoresContainingProductInRange(id, distance, location,  user, req);
        return ResponseEntity.ok(list);
    }

}
