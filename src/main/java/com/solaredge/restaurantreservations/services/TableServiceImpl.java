package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.domain.Table;
import com.solaredge.restaurantreservations.mappers.TableMapper;
import com.solaredge.restaurantreservations.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final TableMapper tableMapper = TableMapper.INSTANCE;

    @Autowired
    public TableServiceImpl(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public TableDto getTableById(Long id) {
        Optional<Table> tableOptional = tableRepository.findById(id);
        return tableMapper.tableToTableDto(tableOptional.get());
    }

    @Override
    public Set<TableDto> getAllTables() {
        return tableRepository.findAll().stream()
                .map(tableMapper::tableToTableDto)
                .collect(Collectors.toSet());
    }

    @Override
    public TableDto createTable(TableDto tableDto) {
        Table savedTable = tableRepository.save(tableMapper.tableDtoToTable(tableDto));
        return tableMapper.tableToTableDto(savedTable);
    }

    @Override
    public void deleteTableById(Long id) {
        tableRepository.deleteById(id);
    }

    @Override
    public TableDto getTableByName(String tableName) {
        return tableMapper.tableToTableDto(tableRepository.findByName(tableName));
    }
}
