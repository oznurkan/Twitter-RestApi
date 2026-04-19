package com.myproject.twitter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

public record RegisterRequestDto(

        @Email
        @NotBlank(message = "Email alanı boş bırakılamaz.")
        @Size(max = 255 )
        String email,

        @NotBlank(message = "Şifre alanı boş bırakılamaz")
        @Size(max = 150 )
        String password,

        @NotBlank(message = "Kullanıcı ismi boş bırakılamaz.")
        @JsonProperty("nickname")
        @Size(max = 150 , min = 3)
        String nickName,

        @Size(max = 100)
        @JsonProperty("firstname")
        String firstName,

        @Size(max = 100)
        @JsonProperty("lastname")
        String lastName,

        @Size(max = 255 )
        @JsonProperty("bio")
        String text,

        Set<String> roles,

        LocalDateTime createdAt
) {
}
