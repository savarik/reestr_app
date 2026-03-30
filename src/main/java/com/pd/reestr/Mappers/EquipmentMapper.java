package com.pd.reestr.Mappers;

import com.pd.reestr.DTO.EquipBigDto;
import com.pd.reestr.Tables.Equipment;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {
    public EquipBigDto toDto(Equipment entity) {
        if (entity == null) return null;
        return EquipBigDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .okid2(entity.getOkid2())
                .measure(entity.getMeasure())
                .price(entity.getPrice())
                .count(entity.getCount())
                .sum(entity.getSum())
                .debet(entity.getDebet())
                .credit(entity.getCredit())
                .country(entity.getCountry())
                .description(entity.getDescription())
                .sourceExel(entity.getSource_exel())
                .build();
    }

    public Equipment toEntity(EquipBigDto dto) {
        if (dto == null) return null;
        return Equipment.builder()
                .id(dto.getId())
                .name(dto.getName())
                .okid2(dto.getOkid2())
                .measure(dto.getMeasure())
                .price(dto.getPrice())
                .count(dto.getCount())
                .sum(dto.getSum())
                .debet(dto.getDebet())
                .credit(dto.getCredit())
                .country(dto.getCountry())
                .description(dto.getDescription())
                .source_exel(dto.getSourceExel())
                .build();
    }
}