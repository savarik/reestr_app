package com.pd.reestr.Tables;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Movements")
public class Movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="date_and_time", nullable = false)
    LocalDateTime date_and_time;

    // ВНЕШНИЙ КЛЮЧ к таблице equipment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    // ВНЕШНИЙ КЛЮЧ к таблице audience (начальная аудитория)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audience_start", nullable = false)
    private Audience startAudience;

    // ВНЕШНИЙ КЛЮЧ к таблице audience (конечная аудитория)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audience_last", nullable = false)
    private Audience endAudience;

    public Movements(LocalDateTime date_and_time, Equipment equipment, Audience startAudience, Audience endAudience) {
        this.date_and_time = date_and_time;
        this.equipment = equipment;
        this.startAudience = startAudience;
        this.endAudience = endAudience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(LocalDateTime date_and_time) {
        this.date_and_time = date_and_time;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Audience getStartAudience() {
        return startAudience;
    }

    public void setStartAudience(Audience startAudience) {
        this.startAudience = startAudience;
    }

    public Audience getEndAudience() {
        return endAudience;
    }

    public void setEndAudience(Audience endAudience) {
        this.endAudience = endAudience;
    }
}
