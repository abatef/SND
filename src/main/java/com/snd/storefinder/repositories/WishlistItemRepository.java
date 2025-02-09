package com.snd.storefinder.repositories;

import com.snd.storefinder.models.wishlist.Wishlistitem;
import com.snd.storefinder.models.wishlist.WishlistitemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemRepository extends JpaRepository<Wishlistitem, WishlistitemId> {
}
