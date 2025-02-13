package com.snd.storefinder.services;

import com.snd.storefinder.exceptions.InvalidWishlistIdException;
import com.snd.storefinder.models.User;
import com.snd.storefinder.models.store.Product;
import com.snd.storefinder.models.wishlist.Wishlist;
import com.snd.storefinder.models.wishlist.Wishlistitem;
import com.snd.storefinder.models.wishlist.WishlistitemId;
import com.snd.storefinder.repositories.WishlistItemRepository;
import com.snd.storefinder.repositories.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductService productService;

    public WishlistService(WishlistRepository wishlistRepository,
                           WishlistItemRepository wishlistItemRepository,
                           ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.productService = productService;
    }

    @Transactional
    public Integer createWishlist(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlistRepository.save(wishlist);
        return wishlist.getId();
    }

    public Wishlist getWishlist(Integer id) {
        return wishlistRepository.findById(id).orElse(null);
    }

    public List<Wishlist> getWishlists() {
        return wishlistRepository.findAll();
    }

    @Transactional
    public Wishlistitem addItemToWishlist(Integer wishlistId, Integer productId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);
        Product product = productService.getProductById(productId);
        return addItemToWishlist(wishlist, product);
    }

    public List<WishlistitemId> getWishlistItemsIds(Integer wishlistId) {
        return wishlistItemRepository
                .findWishlistitemsByWishlist_Id(wishlistId)
                .stream()
                .map(Wishlistitem::getId)
                .toList();
    }

    @Transactional
    public void removeItemFromWishlist(Integer wishlistId, Integer productId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(InvalidWishlistIdException::new);
        Product product = productService.getProductById(productId);
        removeItemFromWishlist(wishlist, product);
    }

    @Transactional
    public Wishlistitem addItemToWishlist(Wishlist wishlist, Product product) {
        Wishlistitem item = new Wishlistitem();
        item.setProduct(product);
        item.setWishlist(wishlist);
        item = wishlistItemRepository.save(item);
        return item;
    }

    @Transactional
    public void removeItemFromWishlist(Wishlist wishlist, Product product) {
        Wishlistitem item = new Wishlistitem();
        item.setProduct(product);
        item.setWishlist(wishlist);
        wishlistItemRepository.delete(item);
    }
}
