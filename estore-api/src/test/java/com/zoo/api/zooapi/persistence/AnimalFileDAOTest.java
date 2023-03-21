package com.zoo.api.zooapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.api.zooapi.model.Animal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Animal File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class AnimalFileDAOTest {
    AnimalFileDAO animalFileDAO;
    Animal[] testAnimals;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException If the file cannot be created
     */
    @BeforeEach
    public void setupAnimalFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAnimals = new Animal[3];
        testAnimals[0] = new Animal(99,"Wi-Fire", "dog", 79);
        testAnimals[1] = new Animal(100,"Galactic Agent", "dog", 79);
        testAnimals[2] = new Animal(101,"Ice Gladiator", "dog", 79);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the animal array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Animal[].class))
                .thenReturn(testAnimals);
        animalFileDAO = new AnimalFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetAnimals() {
        // Invoke
        Animal[] animals = animalFileDAO.getAnimals();

        // Analyze
        assertEquals(animals.length,testAnimals.length);
        for (int i = 0; i < testAnimals.length;++i)
            assertEquals(animals[i],testAnimals[i]);
    }

    @Test
    public void testFindAnimals() {
        // Invoke
        Animal[] animals = animalFileDAO.findAnimals("la");

        // Analyze
        assertEquals(animals.length,2);
        assertEquals(animals[0],testAnimals[1]);
        assertEquals(animals[1],testAnimals[2]);
    }

    @Test
    public void testGetAnimal() {
        // Invoke
        Animal animal = animalFileDAO.getAnimal(99);

        // Analzye
        assertEquals(animal,testAnimals[0]);
    }

    @Test
    public void testDeleteAnimal() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> animalFileDAO.deleteAnimal(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test animals array - 1 (because of the delete)
        // Because animals attribute of AnimalFileDAO is package private
        // we can access it directly
        assertEquals(animalFileDAO.animals.size(),testAnimals.length-1);
    }

    @Test
    public void testCreateAnimal() {
        // Setup
        Animal animal = new Animal(102,"Wonder-Person", "dog", 79);

        // Invoke
        Animal result = assertDoesNotThrow(() -> animalFileDAO.createAnimal(animal),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Animal actual = animalFileDAO.getAnimal(animal.getId());
        assertEquals(actual.getId(),animal.getId());
        assertEquals(actual.getName(),animal.getName());
    }

    @Test
    public void testUpdateAnimal() {
        // Setup
        Animal animal = new Animal(99,"Galactic Agent", "dog", 79);

        // Invoke
        Animal result = assertDoesNotThrow(() -> animalFileDAO.updateAnimal(animal),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Animal actual = animalFileDAO.getAnimal(animal.getId());
        assertEquals(actual,animal);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Animal[].class));

        Animal animal = new Animal(102,"Wi-Fire", "dog", 79);

        assertThrows(IOException.class,
                        () -> animalFileDAO.createAnimal(animal),
                        "IOException not thrown");
    }

    @Test
    public void testGetAnimalNotFound() {
        // Invoke
        Animal animal = animalFileDAO.getAnimal(98);

        // Analyze
        assertEquals(animal,null);
    }

    @Test
    public void testDeleteAnimalNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> animalFileDAO.deleteAnimal(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(animalFileDAO.animals.size(),testAnimals.length);
    }

    @Test
    public void testUpdateAnimalNotFound() {
        // Setup
        Animal animal = new Animal(98,"Bolt", "dog", 79);

        // Invoke
        Animal result = assertDoesNotThrow(() -> animalFileDAO.updateAnimal(animal),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the AnimalFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Animal[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new AnimalFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
