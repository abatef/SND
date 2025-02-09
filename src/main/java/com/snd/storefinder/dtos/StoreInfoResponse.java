package com.snd.storefinder.dtos;

import com.snd.storefinder.models.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfoResponse {
    private Integer id;
    private String name;
    private Address address;
    private Location location;
    private BigDecimal rating;
    private Instant createdAt;
    private Instant updatedAt;
}
