package com.solaredge.restaurantreservations.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.TreeMap;

import static org.junit.Assert.*;

public class TableTest {
    Table table;

    @Before
    public void setUp() throws Exception {
        table = new Table(1L, "Table", 5);
    }

    @Test
    public void testIsAvailableAt() throws Exception {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(2, "Two");
        treeMap.put(4, "Four");
        treeMap.put(1, "One");
        treeMap.put(3, "Three");

        treeMap.values().forEach(s -> System.out.println(s + " "));
    }
}
