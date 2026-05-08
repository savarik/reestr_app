package com.pd.reestr.service;

import com.pd.reestr.DTO.Equipment_country_dto;
import com.pd.reestr.DTO.UpdateDto;
import com.pd.reestr.Mappers.EquipmentMapper;
import com.pd.reestr.Tables.Equipment;
import com.pd.reestr.repository.EquipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
public class CreateService {
    private final EquipmentRepository repository;

    public CreateService(EquipmentRepository repository) {
        this.repository = repository;
    }

    public Equipment_country_dto createEquipment(UpdateDto request) {
        if (repository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Оборудование с именем '" + request.getName() + "' уже существует");
        }

        if(repository.findByName(request.getName())==null||repository.findByName(request.getName()).isEmpty()){
            throw new IllegalArgumentException("Для создания оборудования его название не должно быть пустым");
        }

        Equipment equipment = Equipment.builder()
                .name(request.getName())
                .okid2(request.getOkid2())
                .measure(request.getMeasure())
                .price(request.getPrice())
                .count(request.getCount() != null ? request.getCount() : 0)
                .sum(request.getPrice() != null && request.getCount() != null
                        ? request.getPrice().multiply(BigDecimal.valueOf(request.getCount())).intValue()
                        : 0)
                .country(request.getCountry())
                .description(request.getDescription())
                .build();

        Equipment saved = repository.save(equipment);

        return Equipment_country_dto.builder()
                .name(saved.getName())
                .okid2(saved.getOkid2())
                .count(saved.getCount())
                .country(saved.getCountry())
                .build();
    }
}
