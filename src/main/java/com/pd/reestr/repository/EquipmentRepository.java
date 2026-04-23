package com.pd.reestr.repository;

import com.pd.reestr.Tables.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {
    // Все методы уже есть в JpaRepository и JpaSpecificationExecutor
}