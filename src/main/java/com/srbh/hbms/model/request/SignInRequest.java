package com.srbh.hbms.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignInRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}