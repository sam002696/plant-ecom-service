package com.sami.plant_ecom.service.impl;

import com.sami.plant_ecom.dto.AddressRequest;
import com.sami.plant_ecom.entity.Address;
import com.sami.plant_ecom.entity.User;
import com.sami.plant_ecom.exceptions.CustomMessageException;
import com.sami.plant_ecom.repository.AddressRepository;
import com.sami.plant_ecom.repository.UserRepository;
import com.sami.plant_ecom.responses.AddressResponse;
import com.sami.plant_ecom.security.UserPrincipal;
import com.sami.plant_ecom.service.interfaces.IAddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Address addAddress(AddressRequest addressRequest) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        Long loggedInUserId = userPrincipal.getId();

        User user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new CustomMessageException("Logged-in user not found"));


        Address address = AddressResponse.fromAddressRequest(addressRequest);


        address.setUser(user);

        return addressRepository.save(address);

    }


    @Override
    public List<AddressResponse> getAllAddresses() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        Long loggedInUserId = userPrincipal.getId();

        // Find the user by ID
        User user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new CustomMessageException("Logged-in user not found"));

        // Fetch all addresses for the user and map them to AddressResponse
        List<Address> addresses = addressRepository.findAllByUser(user);

        return addresses.stream()
                .map(AddressResponse::select)
                .collect(Collectors.toList());
    }

}
