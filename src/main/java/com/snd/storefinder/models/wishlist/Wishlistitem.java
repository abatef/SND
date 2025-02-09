package com.snd.storefinder.models.wishlist;

import com.snd.storefinder.models.store.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wishlistitem")
public class Wishlistitem {
    @SequenceGenerator(name = "wishlistitem_id_gen", sequenceName = "wishlist_id_seq", allocationSize = 1)
    @EmbeddedId
    private WishlistitemId id;

    @MapsId("wishlistId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}