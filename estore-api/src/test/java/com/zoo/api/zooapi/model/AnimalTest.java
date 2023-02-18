package com.zoo.api.zooapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Animal class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class AnimalTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";

        // Invoke
        Animal animal = new Animal(expected_id,expected_name);

        // Analyze
        assertEquals(expected_id,animal.getId());
        assertEquals(expected_name,animal.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        Animal animal = new Animal(id,name);

        String expected_name = "Galactic Agent";

        // Invoke
        animal.setName(expected_name);

        // Analyze
        assertEquals(expected_name,animal.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        String expected_string = String.format(Animal.STRING_FORMAT,id,name);
        Animal animal = new Animal(id,name);

        // Invoke
        String actual_string = animal.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}