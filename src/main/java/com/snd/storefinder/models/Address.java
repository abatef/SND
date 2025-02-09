package com.snd.storefinder.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name = "address_line_one", length = 30)
    private String homeAddress;

    @Column(name = "address_line_two", length = 30)
    private String addressLine;

    @Column(name = "city", length = 30)
    private String city;

    @Column(name = "state", length = 30)
    private String state;

    @Column(name = "country", length = 30)
    private String country;

    @Column(name = "zip_code", length = 10)
    private String zipCode;
}
