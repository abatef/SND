package com.snd.storefinder.controllers;

import com.snd.storefinder.models.User;
import com.snd.storefinder.models.wishlist.Wishlistitem;
import com.snd.storefinder.models.wishlist.WishlistitemId;
import com.snd.storefinder.services.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;


    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/product")
    public ResponseEntity<WishlistitemId> addWishlistItem(@RequestParam Integer product,
                                                          @RequestParam Integer wishlist,
                                                          @AuthenticationPrincipal User user) {
        Wishlistitem wishlistitem = wishlistService.addItemToWishlist(wishlist, product);
        return new ResponseEntity<>(wishlistitem.getId(), HttpStatus.OK);
    }

    @DeleteMapping("/product")
    public ResponseEntity<Void> deleteWishlistItem(@RequestParam Integer product,
                                                   @RequestParam Integer wishlist,
                                                   @AuthenticationPrincipal User user) {
        wishlistService.removeItemFromWishlist(wishlist, product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<WishlistitemId>> getWishlistItems(@PathVariable("id") Integer wishlist) {
        List<WishlistitemId> ids = wishlistService.getWishlistItemsIds(wishlist);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }
}
