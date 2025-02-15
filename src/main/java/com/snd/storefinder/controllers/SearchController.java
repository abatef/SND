package com.snd.storefinder.controllers;

import com.snd.storefinder.dtos.product.ProductInfoResponse;
import com.snd.storefinder.dtos.product.StoreProductInfo;
import com.snd.storefinder.models.store.Product;
import com.snd.storefinder.services.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {
    private final SearchService searchService;
    private final ModelMapper modelMapper;

    public SearchController(SearchService searchService, ModelMapper modelMapper) {
        this.searchService = searchService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public List<ProductInfoResponse> search(@RequestParam String query,
                                            @RequestParam(required = false) String category,
                                            @RequestParam(required = false, defaultValue = "1") String page,
                                            @RequestParam(required = false, defaultValue = "10")  String size) {
        List<Product> products = searchService.
                search(query,category ,Integer.parseInt(page), Integer.parseInt(size));
        return products.stream().map(product -> {
            return modelMapper.map(product, ProductInfoResponse.class);
        }).toList();
    }
}
