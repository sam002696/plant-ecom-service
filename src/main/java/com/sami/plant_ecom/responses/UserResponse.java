package com.sami.plant_ecom.responses;


import com.sami.plant_ecom.entity.User;
import lombok.Data;

@Data
public class UserResponse {


    private Long userId;
    private String name;
    private String email;

    public static UserResponse selectUser(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }


}
