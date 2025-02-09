package com.snd.storefinder.dtos.user;

import com.snd.storefinder.dtos.Location;
import com.snd.storefinder.models.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    private String phone;
    private Address address;
    private Location location;
}
