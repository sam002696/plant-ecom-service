package com.sami.plant_ecom.repository;

import com.sami.plant_ecom.entity.Address;
import com.sami.plant_ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUser(User user);
}
