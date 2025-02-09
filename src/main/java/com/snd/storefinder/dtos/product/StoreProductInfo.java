package com.snd.storefinder.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductInfo {
    private Integer productId;
    private Integer storeId;
    private String storeName;
    private BigDecimal price;
    private Integer quantity;
}
