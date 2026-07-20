package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService UserService;

    @GetMapping("/public/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> categories = UserService.getAllUsers();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/public/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {
        UserResponseDto categories = UserService.getUserById(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/public/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserService.createUser(userRequestDto);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            String status = UserService.deleteUser(id);
//            return new ResponseEntity<>(status, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.OK).body(status);
            return ResponseEntity.ok(status);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>("User not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/public/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDto userRequestDto, @PathVariable String id) {
        try{
            boolean updatedUserStatus = UserService.updateUser(userRequestDto,id);
            if(updatedUserStatus){
                return ResponseEntity.ok("User updated successfully");
            }
            return ResponseEntity.notFound().build();
        }catch(ResponseStatusException e){
            return new ResponseEntity<>("User not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
