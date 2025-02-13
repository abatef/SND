package com.snd.storefinder.repositories;

import com.snd.storefinder.models.wishlist.Wishlistitem;
import com.snd.storefinder.models.wishlist.WishlistitemId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistItemRepository extends JpaRepository<Wishlistitem, WishlistitemId> {
    List<Wishlistitem> findWishlistitemsByWishlist_Id(Integer wishlistId);

    List<Wishlistitem> findWishlistitemsByProduct_Id(Integer productId);
}
