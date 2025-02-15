package com.snd.storefinder.models.store;

import com.snd.storefinder.models.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products")
@Document(indexName = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_id_gen")
    @SequenceGenerator(name = "products_id_gen", sequenceName = "products_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    @Field(type = FieldType.Keyword)
    private String name;

    @Column(name = "category", length = 50)
    @Field(type = FieldType.Keyword)
    private String category;

    @Column(name = "description", length = Integer.MAX_VALUE)
    @Field(type = FieldType.Keyword)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "barcode", length = 50)
    private String barcode;

    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(mappedBy = "product")
    private Set<StoreProduct> storeProducts = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "wishlistitem",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "wishlist_id"))
    private Set<Wishlist> wishlists = new LinkedHashSet<>();

}