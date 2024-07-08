package com.skyitschool.skyitschoolecom.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestBody {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String addressLine1;
    private String city;
    private String state;
    private String postalCode;
    private CountryRequestBody country; // Nested object


}
