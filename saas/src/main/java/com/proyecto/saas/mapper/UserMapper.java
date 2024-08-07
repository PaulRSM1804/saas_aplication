package com.proyecto.saas.mapper;

import org.springframework.stereotype.Component;

import com.proyecto.saas.dto.UserDTO;
import com.proyecto.saas.model.User;
import com.proyecto.saas.model.UserType;


@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUserType(user.getUserType().name());
        dto.setPassword(user.getPassword());
        return dto;
    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUserType(UserType.valueOf(dto.getUserType()));
        user.setPassword(dto.getPassword());
        return user;
    }
}

