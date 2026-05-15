package com.pd.reestr.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateDto {
    private String name;
    @Pattern(regexp = "^\\d{2}\\.\\d{2}\\.\\d{2}$", message = "ОКПД2 должен быть в формате XX.XX.XX")
    private String okid2;
    private String measure;
    private BigDecimal price;
    private Integer count;
    private String country;
    private String description;
}
