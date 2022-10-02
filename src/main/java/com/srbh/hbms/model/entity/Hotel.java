package com.srbh.hbms.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;

    @NotBlank
    private String city;

    @NotBlank
    private String hotelName;

    @NotBlank
    @Size(max = 100, message = "Address should not exceed 100 characters")
    private String address;

    @Size(max = 100, message = "Description should not exceed 100 characters")
    private String description;

    @NotNull
    private double avgRatePerDay;

    @NotBlank
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank
    @Size(min = 10, max = 10, message = "Mobile number should be of 10 digits")
    private String phone;

    @URL(message = "Enter a valid web address")
    private String website;

}