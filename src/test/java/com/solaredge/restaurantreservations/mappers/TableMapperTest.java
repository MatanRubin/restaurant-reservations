package com.solaredge.restaurantreservations.mappers;

import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.domain.Table;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TableMapperTest {

    @Before
    public void setUp() throws Exception {
    }

    private void assertObjectEqualsDto(Table table, TableDto tableDto) {
        assertEquals(table.getId(), tableDto.getId());
        assertEquals(table.getCapacity(), tableDto.getCapacity());
        assertEquals(table.getName(), tableDto.getName());
    }

    @Test
    public void tableToTableDto() throws Exception {
        // given
        Table table = new Table(1L, "stam", 4);

        // when
        TableDto tableDto = TableMapper.tableToTableDto(table);

        // then
        assertObjectEqualsDto(table, tableDto);
    }

    @Test
    public void tableDtoToTable() throws Exception {
        // given
        TableDto tableDto = new TableDto(2L, "stam", 5);

        // when
        Table table = TableMapper.tableDtoToTable(tableDto);

        // then
        assertObjectEqualsDto(table, tableDto);
    }

}
