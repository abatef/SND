package com.snd.storefinder.services;

import com.snd.storefinder.dtos.product.ProductCreationRequest;
import com.snd.storefinder.dtos.product.ProductInfoResponse;
import com.snd.storefinder.dtos.product.StoreProductInfo;
import com.snd.storefinder.exceptions.InvalidProductIdException;
import com.snd.storefinder.models.store.Product;
import com.snd.storefinder.repositories.ProductRepository;
import com.snd.storefinder.repositories.StoreProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreProductRepository storeProductRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository,
                          StoreProductRepository storeProductRepository,
                          ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.storeProductRepository = storeProductRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ProductInfoResponse createNewProduct(ProductCreationRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(request, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductInfoResponse.class);
    }


    public List<ProductInfoResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .stream()
                .map(product -> {
                    return modelMapper.map(product, ProductInfoResponse.class);
                }).toList();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).
                orElseThrow(() -> new InvalidProductIdException(id.toString()));
    }

    public ProductInfoResponse getProductInfoById(Integer id) {
        return modelMapper.map(getProductById(id), ProductInfoResponse.class);
    }

    public List<StoreProductInfo> findAllStoreContainingProduct(Integer productId, Pageable pageable) {
        return storeProductRepository.findStoreProductsByProduct_Id(productId,pageable)
                .stream()
                .map(storeProduct ->
                    { return modelMapper.map(storeProduct, StoreProductInfo.class);})
                .toList();
    }

}
