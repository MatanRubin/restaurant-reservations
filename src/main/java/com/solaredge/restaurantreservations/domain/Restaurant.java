package com.solaredge.restaurantreservations.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Synchronized;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "id")
    private Map<Long, Table> tables;

    public Restaurant() {
        this(null, null, null, new HashMap<>());
    }

    public Restaurant(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tables = new HashMap<>();
    }

    public void addTable(Table table) {
        tables.put(table.getId(), table);
    }

    public void removeTable(long tableId) {
        tables.remove(tableId);
    }

    public Set<Table> findAvailableTables(LocalDateTime startTime, LocalDateTime endTime, int nPeople) {
        return tables.values().stream()
                .filter(table -> table.getCapacity() >= nPeople)
                .filter(table -> table.isAvailableAt(startTime, endTime))
                .collect(Collectors.toSet());
    }
}
