package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserRequestDto;
import com.ecommerce.user.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    void createUser(UserRequestDto userRequestDto);

    String deleteUser(String id);

    boolean updateUser(UserRequestDto user, String id);

    UserResponseDto getUserById(String id);
}
