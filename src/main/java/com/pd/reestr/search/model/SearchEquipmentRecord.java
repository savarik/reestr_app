package com.pd.reestr.search.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SearchEquipmentRecord {
    private Long id;
    private String equipmentName;
    private String supplier;
    private LocalDate contractDate;
    private BigDecimal amount;
    private String contractNumber;

    public SearchEquipmentRecord() {
    }

    public SearchEquipmentRecord(Long id, String equipmentName, String supplier, LocalDate contractDate,
                                 BigDecimal amount, String contractNumber) {
        this.id = id;
        this.equipmentName = equipmentName;
        this.supplier = supplier;
        this.contractDate = contractDate;
        this.amount = amount;
        this.contractNumber = contractNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
}
