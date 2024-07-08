package com.skyitschool.skyitschoolecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseBody {
     private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String addressLine1;
    private String city;
    private String state;
    private String postalCode;
    private boolean enabled;
    private String fullName;
    private Integer countryId;
    private String verificationCode;
    private Date createdTime;
    private String authenticationType;
    private String error;

}
