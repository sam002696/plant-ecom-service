package com.sami.plant_ecom.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserInfoChangeRequest {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "User's email address")
    private String email;

    @NotNull(message = "User's phone number")
    private String phoneNumber;

}

