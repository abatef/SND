package com.snd.storefinder.dtos;

import com.snd.storefinder.models.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCreationRequest {
    private String name;
    private Address address;
    private Location location;
}
