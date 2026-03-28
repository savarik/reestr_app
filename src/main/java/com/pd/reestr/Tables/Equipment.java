package com.pd.reestr.Tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Equipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="name", nullable = false)
    String name;

    @Column(name="okid2")
    String okid2;

    @Column(name="measurment")
    String measure;

    @Column(name="price", precision = 11, scale = 2)
    BigDecimal price;

    @Column(name="count")
    int count;

    @Column(name="sum")
    int sum;

    @Column(name="debet", precision = 12,scale = 2)
    BigDecimal debet;

    @Column(name="credit", precision = 12,scale = 2)
    BigDecimal credit;

    @Column(name="country")
    String country;

    @Column(name="description")
    String description;

    @Column(name="sourse")
    String source_exel;

}
