package com.ecommerce.user.service.impl;

import com.ecommerce.user.dto.AddressDTo;
import com.ecommerce.user.dto.UserRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void createUser(UserRequestDto userRequestDto) {
        User user = new User();
        updateUserFromRequest(user, userRequestDto);
        userRepository.save(user);
    }

    @Override
    public String deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userRepository.delete(user);
        return "User " + id + " deleted successfully";
    }

    @Override
    public boolean updateUser(UserRequestDto userRequestDto, String id) {
        return userRepository.findById(id)
                .map(exisitingUser -> {
                    updateUserFromRequest(exisitingUser, userRequestDto);
                    userRepository.save(exisitingUser);
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public UserResponseDto getUserById(String id) {
        UserResponseDto userById = userRepository.findById(id)
                .map(this::mapToUserResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userById;
    }

    private UserResponseDto mapToUserResponse(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        userResponseDto.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTo addressDTo = new AddressDTo();
            addressDTo.setCity(user.getAddress().getCity());
            addressDTo.setCountry(user.getAddress().getCountry());
            addressDTo.setStreet(user.getAddress().getStreet());
            addressDTo.setState(user.getAddress().getState());
            addressDTo.setZipcode(user.getAddress().getZipcode());
            userResponseDto.setAddressDTo(addressDTo);
        }

        return userResponseDto;
    }

    private void updateUserFromRequest(User user, UserRequestDto userRequestDto) {
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        if (userRequestDto.getAddressDTo() != null) {
            Address address = new Address();
            address.setCity(userRequestDto.getAddressDTo().getCity());
            address.setCountry(userRequestDto.getAddressDTo().getCountry());
            address.setStreet(userRequestDto.getAddressDTo().getStreet());
            address.setState(userRequestDto.getAddressDTo().getState());
            address.setZipcode(userRequestDto.getAddressDTo().getZipcode());
            user.setAddress(address);
        }
    }

}
