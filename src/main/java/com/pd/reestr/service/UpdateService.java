package com.pd.reestr.service;

import com.pd.reestr.DTO.UpdateDto;
import com.pd.reestr.Mappers.EquipmentMapper;
import com.pd.reestr.Tables.Equipment;
import com.pd.reestr.repository.EquipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
public class UpdateService {
    private final EquipmentRepository repository;
    private final EquipmentMapper mapper;

    public UpdateService (EquipmentRepository repository, EquipmentMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }

    @Transactional
    public UpdateDto updateEquipment(String name, UpdateDto dto){
        Equipment equipment=repository.findByName(name).orElseThrow(()->new EntityNotFoundException("Оборудование с именем "+name+" не существует в базе данных"));
        if(dto.getName()!=null){
            equipment.setName(dto.getName());
        }
        if(dto.getCount()!=null){
            equipment.setCount(dto.getCount());
        }
        if(dto.getCountry()!=null){
            equipment.setCountry(dto.getCountry());
        }
        if(dto.getMeasure()!=null){
            equipment.setMeasure(dto.getMeasure());
        }
        if(dto.getPrice()!=null){
            equipment.setPrice(dto.getPrice());
        }
        if(dto.getDescription()!=null){
            equipment.setDescription(dto.getDescription());
        }
        if(dto.getOkid2()!=null){
            equipment.setOkid2(dto.getOkid2());
        }

        Equipment saved=repository.save(equipment);

        return UpdateDto.builder().name(saved.getName()).count(saved.getCount()).country(saved.getCountry()).measure(saved.getMeasure()).price(saved.getPrice()).description(saved.getDescription()).okid2(saved.getOkid2()).build();
    }
}
