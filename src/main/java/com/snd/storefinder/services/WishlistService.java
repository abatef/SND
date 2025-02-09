package com.snd.storefinder.services;

import com.snd.storefinder.models.User;
import com.snd.storefinder.models.store.Product;
import com.snd.storefinder.models.wishlist.Wishlist;
import com.snd.storefinder.models.wishlist.Wishlistitem;
import com.snd.storefinder.repositories.WishlistItemRepository;
import com.snd.storefinder.repositories.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;

    public WishlistService(WishlistRepository wishlistRepository, WishlistItemRepository wishlistItemRepository) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Transactional
    public Integer createWishlist(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlistRepository.save(wishlist);
        return wishlist.getId();
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
    public boolean removeItemFromWishlist(Wishlist wishlist, Product product) {
        Wishlistitem item = new Wishlistitem();
        item.setProduct(product);
        item.setWishlist(wishlist);
        wishlistItemRepository.delete(item);
        return true;
    }
}
