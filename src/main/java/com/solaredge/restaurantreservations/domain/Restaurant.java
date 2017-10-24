package com.solaredge.restaurantreservations.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Table> tables;

    public Restaurant() {
        this(null, null, null, new HashSet<>());
    }

    public void addTable(Table table) {
        tables.add(table);
    }
}
