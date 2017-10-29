package com.solaredge.restaurantreservations.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class TableDto {
    private Long id;

    @NotBlank
    private String name;

    @Min(1)
    private int capacity;

    public TableDto() {
        this(null, null, 0);
    }
}
