package com.pd.reestr.Mappers;

import com.pd.reestr.DTO.EquipBigDto;
import com.pd.reestr.DTO.Equipment_country_dto;
import com.pd.reestr.Tables.Equipment;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {
    public EquipBigDto toBigDto(Equipment entity) {
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

    public Equipment BigtoEntity(EquipBigDto dto) {
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

    public Equipment_country_dto toCountryDto(Equipment entity) {
        if (entity == null) return null;
        return Equipment_country_dto.builder()
                .name(entity.getName())
                .okid2(entity.getOkid2())
                .count(entity.getCount())
                .country(entity.getCountry())
                .build();
    }

    public Equipment CountryToEntity(Equipment_country_dto dto){
        if (dto == null) return null;
        return Equipment.builder()
                .name(dto.getName())
                .okid2(dto.getOkid2())
                .count(dto.getCount())
                .country(dto.getCountry())
                .build();
    }

}