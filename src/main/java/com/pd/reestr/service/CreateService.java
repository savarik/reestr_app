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
    private final EquipmentMapper mapper;

    public CreateService(EquipmentRepository repository, EquipmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Equipment_country_dto createEquipment(UpdateDto request) {
        if (repository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Оборудование с именем '" + request.getName() + "' уже существует");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Название оборудования не может быть пустым");
        }

        if (request.getCount()<=0) {
            throw new IllegalArgumentException("количество оборудования должно быть больше 0");
        }

        Equipment equipment = Equipment.builder()
                .name(request.getName())
                .okid2(request.getOkid2())
                .measure(request.getMeasure())
                .price(request.getPrice() != null ? request.getPrice() : BigDecimal.ZERO)
                .count(request.getCount() != null ? request.getCount() : 0)
                .sum(request.getPrice() != null && request.getCount() != null
                        ? request.getPrice().multiply(BigDecimal.valueOf(request.getCount())).intValue()
                        : 0)
                .country(request.getCountry())
                .description(request.getDescription())
                //.debet(BigDecimal.ZERO)
                //.credit(BigDecimal.ZERO)
                .source_exel("web_created")
                .build();

        Equipment saved = repository.save(equipment);

        return mapper.toCountryDto(saved);
    }
}