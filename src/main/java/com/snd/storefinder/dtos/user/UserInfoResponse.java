package com.snd.storefinder.dtos.user;

import com.snd.storefinder.dtos.Location;
import com.snd.storefinder.models.Address;
import com.snd.storefinder.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Address address;
    private Location location;
    private Map<String, Object> preferences;
    private Integer wishlistId;
    private Instant createdAt;
    private Instant updatedAt;
}
