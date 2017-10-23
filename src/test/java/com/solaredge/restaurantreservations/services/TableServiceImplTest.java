package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.domain.Table;
import com.solaredge.restaurantreservations.repositories.TableRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TableServiceImplTest {

    @Mock
    private TableRepository tableRepository;

    private TableService tableService;

    private static final String NAME = "MyTable";
    private static final Long ID = 3L;
    private static final int CAPACITY = 18;

    private Random random = new Random(System.currentTimeMillis());

    private Table generatRandomTable() {
        return new Table(
                random.nextLong(),
                UUID.randomUUID().toString(),
                random.nextInt()
        );
    }

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        tableService = new TableServiceImpl(tableRepository);
    }

    @Test
    public void getTableById() throws Exception {
        // given
        Table table = generatRandomTable();
        when(tableRepository.findById(anyLong())).thenReturn(Optional.of(table));

        // when
        TableDto tableDto = tableService.getTableById(ID);

        // then
        assertEquals(table.getId(), tableDto.getId());
        assertEquals(table.getCapacity(), tableDto.getCapacity());
        assertEquals(table.getName(), tableDto.getName());
    }

    @Test
    public void getAllTables() throws Exception {
        // given
        List<Table> tables = Arrays.asList(
                generatRandomTable(),
                generatRandomTable(),
                generatRandomTable()
        );
        when(tableRepository.findAll()).thenReturn(tables);

        // when
        Set<TableDto> allTables = tableService.getAllTables();

        // then
        assertEquals(tables.size(), allTables.size());
    }

    @Test
    public void createTable() throws Exception {
        // given
        TableDto tableDto = new TableDto(null, NAME, CAPACITY);
        Table table = new Table(ID, NAME, CAPACITY);
        when(tableRepository.save(any())).thenReturn(table);

        // when
        TableDto createdTable = tableService.createTable(tableDto);

        // then
        assertEquals(table.getId(), createdTable.getId());
        assertEquals(table.getName(), createdTable.getName());
        assertEquals(table.getCapacity(), createdTable.getCapacity());
    }

    @Test // TODO improve
    public void deleteTable() throws Exception {
        // given

        // when
//        tableService.deleteTable(ID);
//        TableDto tableById = tableService.getTableById(ID);
//
        // then
//        assertNull(tableById);
    }

}
