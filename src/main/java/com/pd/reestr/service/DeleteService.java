package com.pd.reestr.service;

import com.pd.reestr.Tables.Equipment;
import com.pd.reestr.repository.EquipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeleteService {
    private final EquipmentRepository repositor;

    public DeleteService(EquipmentRepository repositor) {
        this.repositor = repositor;
    }

    public Long findIdByName(String name){
        if(name!=null&&!name.isEmpty()){
            Equipment equipment=repositor.findByName(name).orElseThrow(()->new EntityNotFoundException("Equipment with name "+name+" doesnt found"));
            return equipment.getId();
        }
        else return null;
    }

    @Transactional
    public void deleteEquipment(String name){
        if(!repositor.existsById(findIdByName(name))){

            throw new EntityNotFoundException("Equipment with id "+findIdByName(name)+ "doesnt exists");
        }
        repositor.deleteById(findIdByName(name));
    }
}
