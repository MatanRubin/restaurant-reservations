package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.domain.Table;
import com.solaredge.restaurantreservations.exceptions.NotFoundException;
import com.solaredge.restaurantreservations.mappers.TableMapper;
import com.solaredge.restaurantreservations.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TableService {
    private final TableRepository tableRepository;
    private final TableMapper tableMapper = TableMapper.INSTANCE;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public TableDto getTableById(Long id) {
        Optional<Table> tableOptional = tableRepository.findById(id);
        return tableMapper.tableToTableDto(
                tableOptional.orElseThrow(() -> new NotFoundException("Can't find table with id=" + id)));
    }

    public Set<TableDto> getAllTables() {
        return tableRepository.findAll().stream()
                .map(tableMapper::tableToTableDto)
                .collect(Collectors.toSet());
    }

    public TableDto createTable(TableDto tableDto) {
        Table savedTable = tableRepository.save(tableMapper.tableDtoToTable(tableDto));
        return tableMapper.tableToTableDto(savedTable);
    }

    public void deleteTableById(Long id) {
        tableRepository.deleteById(id);
    }

    public TableDto getTableByName(String tableName) {
        Table table = tableRepository.findByName(tableName);
        if (table == null) {
            throw new NotFoundException("Can't find table with name='" + tableName + "'");
        }
        return tableMapper.tableToTableDto(table);
    }
}
