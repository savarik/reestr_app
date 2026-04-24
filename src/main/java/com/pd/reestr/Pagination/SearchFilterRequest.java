package com.pd.reestr.Pagination;

import java.math.BigDecimal;

public class SearchFilterRequest {
    private String equipmentName;
    private String supplier;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private String country;
    private String okid2;

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

    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
    }

    public BigDecimal getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOkid2() {
        return okid2;
    }

    public void setOkid2(String okid2) {
        this.okid2 = okid2;
    }
}
