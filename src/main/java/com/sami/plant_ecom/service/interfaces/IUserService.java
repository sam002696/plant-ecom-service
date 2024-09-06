package com.sami.plant_ecom.service.interfaces;



import com.sami.plant_ecom.dto.LoginRequest;
import com.sami.plant_ecom.dto.RegisterRequest;
import com.sami.plant_ecom.entity.User;
import com.sami.plant_ecom.responses.LoginResponse;

import java.util.Map;

public interface IUserService {
    User register(RegisterRequest user);

    LoginResponse login(LoginRequest loginRequest);

    Map<String, Object> search(Integer page, Integer size, String sortBy, String search);
}
