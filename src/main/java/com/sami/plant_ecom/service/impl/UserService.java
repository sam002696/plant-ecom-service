package com.sami.plant_ecom.service.impl;

import com.sami.plant_ecom.dto.LoginRequest;
import com.sami.plant_ecom.dto.RegisterRequest;
import com.sami.plant_ecom.entity.User;
import com.sami.plant_ecom.enums.RoleName;
import com.sami.plant_ecom.exceptions.CustomMessageException;
import com.sami.plant_ecom.repository.UserRepository;
import com.sami.plant_ecom.responses.LoginResponse;
import com.sami.plant_ecom.security.UserPrincipal;
import com.sami.plant_ecom.service.CustomUserDetailsService;
import com.sami.plant_ecom.service.interfaces.IUserService;
import com.sami.plant_ecom.utils.JWTUtils;
import com.sami.plant_ecom.utils.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    

    @Override
    public User register(RegisterRequest reqUser) {
        User user = new User();
        if (reqUser.getRole() == null || reqUser.getRole().isEmpty()) {
            reqUser.setRole("USER");
        }
        if (userRepository.existsByEmail(reqUser.getEmail())) {
                throw new CustomMessageException(reqUser.getEmail() + "Already Exists");
            }
        reqUser.setPassword(passwordEncoder.encode(reqUser.getPassword()));

        user.setEmail(reqUser.getEmail());
        user.setName(reqUser.getName());
        user.setRole(RoleName.valueOf(reqUser.getRole()));
        user.setPassword(reqUser.getPassword());
        user.setPhoneNumber(reqUser.getPhoneNumber());

        return userRepository.save(user);

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        var user = userDetailsService.loadUserByUsernameAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        var token = jwtUtils.generateToken(user);

        UserPrincipal userPrincipal = (UserPrincipal) user;

        String role = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

            loginResponse.setId(userPrincipal.getId());
            loginResponse.setUsername(userPrincipal.getName());
            loginResponse.setEmail(userPrincipal.getEmail());
            loginResponse.setRole(role);
            loginResponse.setAccessToken(token);
            loginResponse.setExpirationTime("6 days");

            return loginResponse;

    }



    @Override
    public Map<String, Object> search(Integer page, Integer size, String sortBy, String search) {
        ServiceHelper<User> serviceHelper = new ServiceHelper<>(User.class);
        return serviceHelper.getList(
                userRepository.search(search, serviceHelper.getPageable(sortBy, page, size)),
                page, size);
    }
    
}
