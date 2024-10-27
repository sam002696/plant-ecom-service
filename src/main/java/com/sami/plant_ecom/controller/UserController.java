package com.sami.plant_ecom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sami.plant_ecom.dto.UserInfoChangeRequest;
import com.sami.plant_ecom.entity.User;
import com.sami.plant_ecom.responses.UserResponse;
import com.sami.plant_ecom.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import java.io.IOException;

import static com.sami.plant_ecom.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "User API")
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update user information", responses = {
            @ApiResponse(description = "User updated successfully",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))
    })
    public ResponseEntity<JSONObject> updateUser(
            @RequestPart("userInfo") String userInfoJson,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {


        UserInfoChangeRequest userInfoChangeRequest = convertToUserInfoChangeRequest(userInfoJson);


        UserResponse userResponse = userService.updateUserInfo(userInfoChangeRequest, profileImage);

        return ok(success(userResponse, "User updated successfully").getJson());
    }

    private UserInfoChangeRequest convertToUserInfoChangeRequest(String userInfoJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(userInfoJson, UserInfoChangeRequest.class);
    }





}
