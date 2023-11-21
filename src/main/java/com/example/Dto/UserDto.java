package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String manv;
    private String fullname;
    private Integer age;
    private String address;
    private String phone;
    private String email;
    private String gender;
}
