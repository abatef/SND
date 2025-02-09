package com.snd.storefinder.services;

import com.snd.storefinder.dtos.Location;
import com.snd.storefinder.dtos.StoreCreationRequest;
import com.snd.storefinder.dtos.StoreInfoResponse;
import com.snd.storefinder.dtos.product.StoreProductInfo;
import com.snd.storefinder.enums.UserRole;
import com.snd.storefinder.exceptions.*;
import com.snd.storefinder.models.User;
import com.snd.storefinder.models.store.*;
import com.snd.storefinder.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreManagerRepository storeManagerRepository;
    private final ModelMapper modelMapper;
    private final StoreProductRepository storeProductRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public StoreService(StoreRepository storeRepository,
                        StoreManagerRepository storeManagerRepository,
                        ModelMapper modelMapper, StoreProductRepository storeProductRepository,
                        UserRepository userRepository, UserService userService,
                        ProductRepository productRepository, ProductService productService) {
        this.storeRepository = storeRepository;
        this.storeManagerRepository = storeManagerRepository;
        this.modelMapper = modelMapper;
        this.storeProductRepository = storeProductRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Transactional
    @PreAuthorize("hasRole('OWNER')")
    public StoreInfoResponse createStore(StoreCreationRequest request, User user) {
        Store store = modelMapper.map(request, Store.class);
        store = storeRepository.save(store);
        StoreManager manager = new StoreManager();
        manager.setStore(store);
        manager.setUser(user);
        manager.setUserRole(UserRole.OWNER);
        storeManagerRepository.save(manager);
        return modelMapper.map(store, StoreInfoResponse.class);
    }

    @Transactional
    @PreAuthorize("hasRole('MANGER')")
    public StoreManager addStoreAdmin(Store store, User user, UserRole userRole, User principal) {
        StoreManagerId id = new StoreManagerId();
        id.setStoreId(store.getId());
        id.setUserId(user.getId());
        if (storeManagerRepository.existsById(id)) {
            throw new InvalidStoreManagerId("Store manager already exists");
        }
        StoreManager manager = new StoreManager();
        manager.setStore(store);
        manager.setUser(user);
        manager.setUserRole(userRole);
        storeManagerRepository.save(manager);
        return manager;
    }

    @Transactional
    @PreAuthorize("hasRole('MANGER')")
    public StoreManagerId addStoreAdmin(Integer storeId, Integer userId, UserRole userRole, User principal) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new InvalidStoreIdentifierException(storeId.toString()));
        User manager = userService.findUserByIdOrEmail(null, userId);
        return addStoreAdmin(store, manager, userRole, principal).getId();
    }

    private void validStoreId(Integer storeId) {
        if (storeRepository.existsById(storeId)) {
            return;
        }
        throw new InvalidStoreIdentifierException(storeId.toString());
    }

    public Store getStoreById(Integer storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new InvalidStoreIdentifierException(storeId.toString()));
    }

    @Transactional
    @PreAuthorize("hasRole('MANGER')")
    public void removeStoreAdmin(Integer storeId, Integer managerId, User principal) {
        validStoreId(storeId);
        userService.validUserId(managerId);
        StoreManagerId id = new StoreManagerId();
        id.setStoreId(storeId);
        id.setUserId(managerId);
        if (storeManagerRepository.existsById(id)) {
            storeManagerRepository.deleteById(id);
            return;
        }
        throw new InvalidStoreManagerId("Invalid Store Manager");
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public StoreProductInfo addProductToStore(Integer productId, Integer storeId, User user) {
        if (storeProductRepository.existsById(new StoreProductId(storeId, productId))) {
            throw new StoreProductAlreadyExistsException("Product already exists");
        }
        Product product = productService.getProductById(productId);
        Store store = getStoreById(storeId);
        StoreProduct storeProduct = new StoreProduct();
        storeProduct.setProduct(product);
        storeProduct.setStore(store);
        storeProductRepository.save(storeProduct);
        return modelMapper.map(storeProduct, StoreProductInfo.class);
    }

    public StoreProduct getStoreProduct(Integer storeId, Integer productId) {
        StoreProductId id = new StoreProductId(storeId, productId);
        return storeProductRepository.findById(id)
                .orElseThrow(() -> new InvalidStoreProductIdException(id.toString()));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public StoreProductInfo updateProductStock(Integer productId, Integer storeId, Integer addedStock, User user) {
        StoreProduct product = getStoreProduct(productId, storeId);
        product.setQuantity(product.getQuantity() + addedStock);
        storeProductRepository.save(product);
        return modelMapper.map(product, StoreProductInfo.class);
    }

    @Transactional
    @PreAuthorize("hasRole('MANGER')")
    public StoreProductInfo updateProductPrice(Integer productId, Integer storeId, Double price, User user) {
        StoreProduct product = getStoreProduct(productId, storeId);
        product.setPrice(BigDecimal.valueOf(price));
        storeProductRepository.save(product);
        return modelMapper.map(product, StoreProductInfo.class);
    }

    public StoreProductInfo getStoreProductInfo(Integer storeId, Integer productId, User user) {
        StoreProductId id = new StoreProductId();
        id.setStoreId(storeId);
        id.setProductId(productId);
        StoreProduct storeProduct = storeProductRepository.findById(id)
                .orElseThrow(() -> new InvalidStoreProductIdException(id.toString()));
        return modelMapper.map(storeProduct, StoreProductInfo.class);
    }

    public List<StoreProductInfo> getAllProductsOfStore(Integer storeId, User user, Pageable pageable) {
        validStoreId(storeId);
        return storeProductRepository.findStoreProductsByStore_Id(storeId, pageable)
                .stream()
                .map(storeProduct -> {
                    return modelMapper.map(storeProduct, StoreProductInfo.class);
                }).toList();
    }

    public List<StoreProductInfo> getAllStoresContainingProductInRange(Integer productId, Double distance,
                                                                       Location current, User user, Pageable pageable) {
        return storeProductRepository
                .findStoreProductInRangeByProduct_Id(productId, current.getLongitude(), current.getLatitude(), distance, pageable)
                .stream()
                .map(storeProduct -> {
                    return modelMapper.map(storeProduct, StoreProductInfo.class);
                }).toList();
    }

    @Transactional
    @PreAuthorize("hasRole('MANGER')")
    public void removeProductFromStore(Integer productId, Integer storeId, User user) {
        StoreProductId id = new StoreProductId();
        id.setProductId(productId);
        id.setStoreId(storeId);
        storeProductRepository.deleteById(id);
    }
}
