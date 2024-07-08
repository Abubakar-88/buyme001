package com.skyitschool.skyitschoolecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseBody {

    private Integer id;
    private String name;
    private String code;
    private StateResponseBody state; // Only include the selected state
}
