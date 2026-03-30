package com.pd.reestr.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdinaryDto {
    private String okid2;
    private String name;
    private int measurement;
    private int count;

}
