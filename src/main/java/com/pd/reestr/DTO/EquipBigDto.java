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
public class EquipBigDto {
    private Long id;
    private String name;
    private String okid2;
    private String measure;
    private BigDecimal price;
    private int count;
    private int sum;
    private BigDecimal debet;
    private BigDecimal credit;
    private String country;
    private String description;
    private String sourceExel;   // source_exel
}