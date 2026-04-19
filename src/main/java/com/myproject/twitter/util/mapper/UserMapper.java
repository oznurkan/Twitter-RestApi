package com.myproject.twitter.util.mapper;


import com.myproject.twitter.dto.request.RegisterRequestDto;
import com.myproject.twitter.dto.response.UserResponseDto;


import com.myproject.twitter.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {


    public UserResponseDto toResponseDto(User user){

        return new UserResponseDto(
                user.getNickName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getText(),
                user.getCreatedAt(),
                user.getUpdatedAt()

        );
    }

    public User toEntity(RegisterRequestDto registerRequestDto){

        User user = new User();

        user.setFirstName(registerRequestDto.firstName());
        user.setLastName(registerRequestDto.lastName());
        user.setNickName(registerRequestDto.nickName());
        user.setEmail(registerRequestDto.email());
        user.setText(registerRequestDto.text());

        if( registerRequestDto.createdAt() != null){
            user.setUpdatedAt(LocalDateTime.now());

        }else{
            user.setCreatedAt(LocalDateTime.now());
        }


        return user;

    }


}
