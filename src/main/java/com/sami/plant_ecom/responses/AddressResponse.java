package com.sami.plant_ecom.responses;


import com.sami.plant_ecom.dto.AddressRequest;
import com.sami.plant_ecom.entity.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AddressResponse implements Serializable {

    private Long id;
    private String addressType;
    private String streetAddress;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    // Static factory method to convert Address entity to AddressResponse DTO
    public static AddressResponse select(Address address) {
        if (address == null) {
            return null;
        }

        AddressResponse response = new AddressResponse();

        response.setId(address.getId());
        response.setAddressType(address.getAddressType());
        response.setStreetAddress(address.getStreetAddress());
        response.setAddressLine2(address.getAddressLine2());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setZipcode(address.getZipcode());
        response.setCountry(address.getCountry());

        return response;
    }

    // Static method to convert addressRequest to Address entity
    public static Address fromAddressRequest(AddressRequest addressRequest) {
        if (addressRequest == null) {
            return null;
        }

        Address address = new Address();

        address.setAddressType(addressRequest.getAddressType());
        address.setStreetAddress(addressRequest.getStreetAddress());
        address.setAddressLine2(addressRequest.getAddressLine2());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setZipcode(addressRequest.getZipcode());
        address.setCountry(addressRequest.getCountry());

        return address;
    }
}
