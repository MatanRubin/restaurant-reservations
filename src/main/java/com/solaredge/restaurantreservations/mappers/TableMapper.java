package com.solaredge.restaurantreservations.mappers;

import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.domain.Table;

public class TableMapper {

    public static final TableMapper INSTANCE = new TableMapper();

    public TableDto tableToTableDto(Table table) {
        if (table == null) {
            return null;
        }

        return new TableDto(
                table.getId(),
                table.getName(),
                table.getCapacity()
        );
    }

    public Table tableDtoToTable(TableDto tableDto) {
        if (tableDto == null) {
            return null;
        }
        return new Table(
                tableDto.getId(),
                tableDto.getName(),
                tableDto.getCapacity()
        );
    }
}
