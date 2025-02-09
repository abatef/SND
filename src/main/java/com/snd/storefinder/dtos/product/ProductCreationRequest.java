package com.snd.storefinder.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreationRequest {
    private String name;
    private String category;
    private String description;
    private String imageUrl;
    private String barcode;
}
