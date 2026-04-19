package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.RegisterRequestDto;
import com.myproject.twitter.dto.response.UserResponseDto;
import com.myproject.twitter.entity.Role;
import com.myproject.twitter.entity.User;
import com.myproject.twitter.exception.UserAlreadyRegisteredException;
import com.myproject.twitter.repository.RoleRepository;
import com.myproject.twitter.repository.UserRepository;
import com.myproject.twitter.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponseDto register(RegisterRequestDto registerRequestDto) {

        if(userRepository.findByEmail(registerRequestDto.email()).isPresent())
            throw new UserAlreadyRegisteredException("Email already registered!");

        User user = userMapper.toEntity(registerRequestDto);

        user.setPassword(passwordEncoder.encode(registerRequestDto.password()));

        if (registerRequestDto.roles() != null && !registerRequestDto.roles().isEmpty()) {
            registerRequestDto.roles().forEach(roleName -> {
                Role role = roleRepository.getByAuthority(roleName)
                        .orElseThrow(() -> new RuntimeException("Hata: " + roleName + " rolü bulunamadı!"));
                user.getRoles().add(role);
            });
        } else {
            Role userRole = roleRepository.getByAuthority("USER")
                    .orElseThrow(() -> new RuntimeException("Hata: USER rolü bulunamadı!"));
            user.getRoles().add(userRole);
        }

        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    public UserResponseDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        return userMapper.toResponseDto(user);
    }



}
