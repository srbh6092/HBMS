package com.srbh.hbms.model.request;

import lombok.*;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddHotelRequest {

    @NotBlank
    private String city;

    @NotBlank
    private String hotelName;

    @NotBlank
    @Size(max = 100, message = "Address should not exceed 100 characters")
    private String address;

    @Size(max = 100, message = "Description should not exceed 100 characters")
    private String description;

    @NotBlank
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank
    @Size(min = 10, max = 10, message = "Mobile number should be of 10 digits")
    private String phone;

    @URL(message = "Enter a valid web address")
    private String website;

}
