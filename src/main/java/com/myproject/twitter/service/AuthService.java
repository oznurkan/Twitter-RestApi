package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.RegisterRequestDto;
import com.myproject.twitter.dto.response.UserResponseDto;

public interface AuthService {

    UserResponseDto register(RegisterRequestDto registerRequestDto);

    UserResponseDto getUserByEmail(String email);


}
