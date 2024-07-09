package com.buyme.buymeEcom.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrackResponse {
    private String status;
    private Date updateTime;
    private String note;

    // Getters and Setters
}