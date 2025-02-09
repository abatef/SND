package com.snd.storefinder.dtos.product;

import com.snd.storefinder.models.store.StoreProduct;
import com.snd.storefinder.models.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoResponse {
    private Integer id;
    private String name;
    private String category;
    private String description;
    private String imageUrl;
    private String barcode;
    private BigDecimal averageRating;
    private Instant createdAt;
    private Instant updatedAt;
}
