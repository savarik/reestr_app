package com.pd.reestr.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipment_country_dto {
    private String name;
    private String okid2;
    private int count;
    private String country;
}