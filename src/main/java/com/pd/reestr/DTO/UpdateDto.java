package com.pd.reestr.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateDto {
    private String name;
    private String okid2;
    private String measure;
    private BigDecimal price;
    private Integer count;
    private String country;
    private String description;
}
