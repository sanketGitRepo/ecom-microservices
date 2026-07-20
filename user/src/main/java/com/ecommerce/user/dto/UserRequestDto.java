package com.ecommerce.user.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AddressDTo addressDTo;
}
