package com.srbh.hbms.model.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
@Builder
@Getter
public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 10, message = "Username should have 4 - 10 characters")
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Email(message = "Enter valid email address")
    private String email;

    @NotBlank
    @Size(min = 10, max = 10, message = "Mobile number should be of 10 digits")
    private String mobile;

    private String role;
}
