package com.snd.storefinder.models.wishlist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class WishlistitemId implements java.io.Serializable {
    private static final long serialVersionUID = -4441860770941659478L;
    @Column(name = "wishlist_id", nullable = false)
    private Integer wishlistId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WishlistitemId entity = (WishlistitemId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.wishlistId, entity.wishlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, wishlistId);
    }

}