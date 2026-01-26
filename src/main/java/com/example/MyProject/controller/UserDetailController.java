package com.example.MyProject.controller;

import com.example.MyProject.dto.UserDetailDTO;
import com.example.MyProject.model.UserDetail;
import com.example.MyProject.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.MyProject.dto.ApiResponse;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // allows React frontend
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody UserDetailDTO userDetailDTO) {
        try {
            UserDetail newUser = userDetailService.addUser(userDetailDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse(true, "User created successfully", newUser)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
}