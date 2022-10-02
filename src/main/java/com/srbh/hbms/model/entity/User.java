package com.srbh.hbms.model.entity;

import com.srbh.hbms.model.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

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

    @Enumerated(EnumType.STRING)
    private Role role;

}