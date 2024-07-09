package com.buyme.buymeEcom.loginDTO;

import com.buyme.buymeEcom.entity.AbstractAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends AbstractAddress {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String email;

}
