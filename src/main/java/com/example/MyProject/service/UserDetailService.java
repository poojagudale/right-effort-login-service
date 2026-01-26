package com.example.MyProject.service;

import com.example.MyProject.dto.UserDetailDTO;
import com.example.MyProject.model.UserDetail;
import com.example.MyProject.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    public UserDetail addUser(UserDetailDTO userDetailDTO) {
        // Check if email already exists
        Optional<UserDetail> existingEmail = userDetailRepository.findByEmail(userDetailDTO.getEmail());
        if (existingEmail.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Check if mobile number already exists
        Optional<UserDetail> existingMobile = userDetailRepository.findByMobileNo(userDetailDTO.getMobileNo());
        if (existingMobile.isPresent()) {
            throw new IllegalArgumentException("Mobile number already exists");
        }

        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName(userDetailDTO.getFirstName());
        userDetail.setLastName(userDetailDTO.getLastName());
        userDetail.setEmail(userDetailDTO.getEmail());
        userDetail.setMobileNo(userDetailDTO.getMobileNo());

        return userDetailRepository.save(userDetail);
    }
}