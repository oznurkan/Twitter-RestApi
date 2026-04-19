package com.myproject.twitter.controller;


import com.myproject.twitter.dto.request.RegisterRequestDto;
import com.myproject.twitter.dto.response.UserResponseDto;
import com.myproject.twitter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody RegisterRequestDto registerRequestDto){

        return authService.register(registerRequestDto);
    }


    @PostMapping("/login")
    public UserResponseDto login(@AuthenticationPrincipal UserDetails userDetails) {
        return authService.getUserByEmail(userDetails.getUsername());
    }




}
