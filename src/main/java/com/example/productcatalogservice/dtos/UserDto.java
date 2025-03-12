package com.example.productcatalogservice.dtos;

import com.example.productcatalogservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;

    private Set<Role> roleSet = new HashSet<>();
}
