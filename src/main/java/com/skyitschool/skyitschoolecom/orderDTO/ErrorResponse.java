package com.skyitschool.skyitschoolecom.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract  class ErrorResponse {
    private String message;

}
