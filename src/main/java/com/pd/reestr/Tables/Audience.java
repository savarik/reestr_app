package com.pd.reestr.Tables;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Audience")
public class Audience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="number",nullable = false)
    String number;

    @OneToMany(mappedBy = "currentAudience")
    private List<Equipment> currentEquipments = new ArrayList<>();

    @OneToMany(mappedBy = "startAudience")
    private List<Movements> startMovements = new ArrayList<>();

    @OneToMany(mappedBy = "endAudience")
    private List<Movements> endMovements = new ArrayList<>();

    public Audience(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Equipment> getCurrentEquipments() {
        return currentEquipments;
    }

    public void setCurrentEquipments(List<Equipment> currentEquipments) {
        this.currentEquipments = currentEquipments;
    }

    public List<Movements> getStartMovements() {
        return startMovements;
    }

    public void setStartMovements(List<Movements> startMovements) {
        this.startMovements = startMovements;
    }

    public List<Movements> getEndMovements() {
        return endMovements;
    }

    public void setEndMovements(List<Movements> endMovements) {
        this.endMovements = endMovements;
    }
}
