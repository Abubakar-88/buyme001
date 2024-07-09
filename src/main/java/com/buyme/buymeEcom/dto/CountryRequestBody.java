package com.buyme.buymeEcom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryRequestBody {
    private Integer id;
    private StateRequestBody state;
}