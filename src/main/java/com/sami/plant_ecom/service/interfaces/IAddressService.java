package com.sami.plant_ecom.service.interfaces;

import com.sami.plant_ecom.dto.AddressRequest;
import com.sami.plant_ecom.entity.Address;
import com.sami.plant_ecom.responses.AddressResponse;

import java.util.List;

public interface IAddressService {
    Address addAddress(AddressRequest addressRequest);

    List<AddressResponse> getAllAddresses();
}
