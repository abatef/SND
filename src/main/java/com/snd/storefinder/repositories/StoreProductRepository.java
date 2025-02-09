package com.snd.storefinder.repositories;

import com.snd.storefinder.models.store.StoreProduct;
import com.snd.storefinder.models.store.StoreProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, StoreProductId> {
    Page<StoreProduct> findStoreProductsByProduct_Id(Integer productId, Pageable pageable);

    Page<StoreProduct> findStoreProductsByStore_Id(Integer storeId, Pageable pageable);

   @Query(value = """
        SELECT sp.* FROM storeproduct sp
        JOIN stores s ON sp.store_id = s.id
        WHERE sp.product_id = :productId
          AND ST_DWithin(
              s.location,
              ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326),
              :distance
          )
   \s""", nativeQuery = true)
    Page<StoreProduct> findStoreProductInRangeByProduct_Id(
            Integer productId,
            double longitude,
            double latitude,
            double distance,
            Pageable pageable
    );
}
