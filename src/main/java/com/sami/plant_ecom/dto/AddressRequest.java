package com.sami.plant_ecom.dto;

import lombok.Data;

@Data
public class AddressRequest {
    private String addressType;
    private String streetAddress;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
