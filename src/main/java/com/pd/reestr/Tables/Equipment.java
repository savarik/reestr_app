package com.pd.reestr.Tables;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;



    @Column(name="model",nullable = false)
    String model;



    @Column(name="status",nullable = false)
    String status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // ВНЕШНИЙ КЛЮЧ к таблице audience (текущее местоположение)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audience_id", nullable = false)
    private Audience currentAudience;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movements> movements = new ArrayList<>();

    public Equipment(Audience currentAudience, Category category, String status, String model) {
        this.currentAudience = currentAudience;
        this.category = category;
        this.status = status;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Audience getCurrentAudience() {
        return currentAudience;
    }

    public void setCurrentAudience(Audience currentAudience) {
        this.currentAudience = currentAudience;
    }

    public List<Movements> getMovements() {
        return movements;
    }

    public void setMovements(List<Movements> movements) {
        this.movements = movements;
    }
}
