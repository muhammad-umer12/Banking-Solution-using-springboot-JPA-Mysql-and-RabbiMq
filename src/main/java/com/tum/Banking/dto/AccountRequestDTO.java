package com.tum.Banking.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountRequestDTO {

    private int customerId;
    private List<String> currency;
    private String country;

}
