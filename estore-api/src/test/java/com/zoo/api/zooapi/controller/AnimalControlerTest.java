package com.zoo.api.zooapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.zoo.api.zooapi.persistence.AnimalDAO;
import com.zoo.api.zooapi.model.Animal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Animal Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class AnimalControlerTest {
    private AnimalController animalController;
    private AnimalDAO mockAnimalDAO;

    /**
     * Before each test, create a new AnimalController object and inject
     * a mock Animal DAO
     */
    @BeforeEach
    public void setupAnimalController() {
        mockAnimalDAO = mock(AnimalDAO.class);
        animalController = new AnimalController(mockAnimalDAO);
    }

    @Test
    public void testGetAnimal() throws IOException {  // getAnimal may throw IOException
        // Setup
        Animal animal = new Animal(99,"Galactic Agent");
        // When the same id is passed in, our mock Animal DAO will return the Animal object
        when(mockAnimalDAO.getAnimal(animal.getId())).thenReturn(animal);

        // Invoke
        ResponseEntity<Animal> response = animalController.getAnimal(animal.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(animal,response.getBody());
    }

    @Test
    public void testGetAnimalNotFound() throws Exception { // createAnimal may throw IOException
        // Setup
        int animalId = 99;
        // When the same id is passed in, our mock Animal DAO will return null, simulating
        // no animal found
        when(mockAnimalDAO.getAnimal(animalId)).thenReturn(null);

        // Invoke
        ResponseEntity<Animal> response = animalController.getAnimal(animalId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetAnimalHandleException() throws Exception { // createAnimal may throw IOException
        // Setup
        int animalId = 99;
        // When getAnimal is called on the Mock Animal DAO, throw an IOException
        doThrow(new IOException()).when(mockAnimalDAO).getAnimal(animalId);

        // Invoke
        ResponseEntity<Animal> response = animalController.getAnimal(animalId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all AnimalController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateAnimal() throws IOException {  // createAnimal may throw IOException
        // Setup
        Animal animal = new Animal(99,"Wi-Fire");
        // when createAnimal is called, return true simulating successful
        // creation and save
        when(mockAnimalDAO.createAnimal(animal)).thenReturn(animal);

        // Invoke
        ResponseEntity<Animal> response = animalController.createAnimal(animal);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(animal,response.getBody());
    }

    @Test
    public void testCreateAnimalFailed() throws IOException {  // createAnimal may throw IOException
        // Setup
        Animal animal = new Animal(99,"Bolt");
        // when createAnimal is called, return false simulating failed
        // creation and save
        when(mockAnimalDAO.createAnimal(animal)).thenReturn(null);

        // Invoke
        ResponseEntity<Animal> response = animalController.createAnimal(animal);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateAnimalHandleException() throws IOException {  // createAnimal may throw IOException
        // Setup
        Animal animal = new Animal(99,"Ice Gladiator");

        // When createAnimal is called on the Mock Animal DAO, throw an IOException
        doThrow(new IOException()).when(mockAnimalDAO).createAnimal(animal);

        // Invoke
        ResponseEntity<Animal> response = animalController.createAnimal(animal);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateAnimal() throws IOException { // updateAnimal may throw IOException
        // Setup
        Animal animal = new Animal(99,"Wi-Fire");
        // when updateAnimal is called, return true simulating successful
        // update and save
        when(mockAnimalDAO.updateAnimal(animal)).thenReturn(animal);
        ResponseEntity<Animal> response = animalController.updateAnimal(animal);
        animal.setName("Bolt");

        // Invoke
        response = animalController.updateAnimal(animal);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(animal,response.getBody());
    }

    @Test
    public void testUpdateAnimalFailed() throws IOException { // updateAnimal may throw IOException
        // Setup
        Animal animal = new Animal(99,"Galactic Agent");
        // when updateAnimal is called, return true simulating successful
        // update and save
        when(mockAnimalDAO.updateAnimal(animal)).thenReturn(null);

        // Invoke
        ResponseEntity<Animal> response = animalController.updateAnimal(animal);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateAnimalHandleException() throws IOException { // updateAnimal may throw IOException
        // Setup
        Animal animal = new Animal(99,"Galactic Agent");
        // When updateAnimal is called on the Mock Animal DAO, throw an IOException
        doThrow(new IOException()).when(mockAnimalDAO).updateAnimal(animal);

        // Invoke
        ResponseEntity<Animal> response = animalController.updateAnimal(animal);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetAnimales() throws IOException { // getAnimales may throw IOException
        // Setup
        Animal[] animales = new Animal[2];
        animales[0] = new Animal(99,"Bolt");
        animales[1] = new Animal(100,"The Great Iguana");
        // When getAnimales is called return the animales created above
        when(mockAnimalDAO.getAnimals()).thenReturn(animales);

        // Invoke
        ResponseEntity<Animal[]> response = animalController.getAnimals();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(animales,response.getBody());
    }

    @Test
    public void testGetAnimalesHandleException() throws IOException { // getAnimales may throw IOException
        // Setup
        // When getAnimales is called on the Mock Animal DAO, throw an IOException
        doThrow(new IOException()).when(mockAnimalDAO).getAnimals();

        // Invoke
        ResponseEntity<Animal[]> response = animalController.getAnimals();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchAnimales() throws IOException { // findAnimales may throw IOException
        // Setup
        String searchString = "Mon";
        Animal[] animales = new Animal[2];
        animales[0] = new Animal(25,"Monkey");
        animales[1] = new Animal(26,"Mongoose");
        // When findAnimales is called with the search string, return the two
        /// animales above
        when(mockAnimalDAO.findAnimals(searchString)).thenReturn(animales);

        // Invoke
        ResponseEntity<Animal[]> response = animalController.searchAnimals(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(animales,response.getBody());
    }

    @Test
    public void testSearchAnimalesHandleException() throws IOException { // findAnimales may throw IOException
        // Setup
        String searchString = "an";
        // When createAnimal is called on the Mock Animal DAO, throw an IOException
        doThrow(new IOException()).when(mockAnimalDAO).findAnimals(searchString);

        // Invoke
        ResponseEntity<Animal[]> response = animalController.searchAnimals(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteAnimal() throws IOException { // deleteAnimal may throw IOException
        // Setup
        int animalId = 99;
        // when deleteAnimal is called return true, simulating successful deletion
        when(mockAnimalDAO.deleteAnimal(animalId)).thenReturn(true);

        // Invoke
        ResponseEntity<Animal> response = animalController.deleteAnimal(animalId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteAnimalNotFound() throws IOException { // deleteAnimal may throw IOException
        // Setup
        int animalId = 99;
        // when deleteAnimal is called return false, simulating failed deletion
        when(mockAnimalDAO.deleteAnimal(animalId)).thenReturn(false);

        // Invoke
        ResponseEntity<Animal> response = animalController.deleteAnimal(animalId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteAnimalHandleException() throws IOException { // deleteAnimal may throw IOException
        // Setup
        int animalId = 99;
        // When deleteAnimal is called on the Mock Animal DAO, throw an IOException
        doThrow(new IOException()).when(mockAnimalDAO).deleteAnimal(animalId);

        // Invoke
        ResponseEntity<Animal> response = animalController.deleteAnimal(animalId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}