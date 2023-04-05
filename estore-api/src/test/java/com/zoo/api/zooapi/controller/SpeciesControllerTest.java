package com.zoo.api.zooapi.controller;

import com.zoo.api.zooapi.model.Species;
import com.zoo.api.zooapi.persistence.SpeciesDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test the Species Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class SpeciesControllerTest {
    private SpeciesController speciesController;
    private SpeciesDAO mockSpeciesDAO;

    /**
     * Before each test, create a new SpeciesController object and inject
     * a mock Species DAO
     */
    @BeforeEach
    public void setupSpeciesController() {
        mockSpeciesDAO = mock(SpeciesDAO.class);
        speciesController = new SpeciesController(mockSpeciesDAO);
    }

    @Test
    public void testGetSpecies() throws IOException {  // getSpecies may throw IOException
        // Setup
        String[] test = {"one", "two", "three"}; 
        Species species = new Species("mongoose", test);
        // When the same id is passed in, our mock Species DAO will return the Species object
        when(mockSpeciesDAO.getSpecies(species.getName())).thenReturn(species);

        // Invoke
        ResponseEntity<Species> response = speciesController.getSpecies(species.getName());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(species,response.getBody());
    }

    @Test
    public void testGetSpeciesNotFound() throws Exception { // createSpecies may throw IOException
        // Setup
        String speciesId = "test";
        // When the same id is passed in, our mock Species DAO will return null, simulating
        // no species found
        when(mockSpeciesDAO.getSpecies(speciesId)).thenReturn(null);

        // Invoke
        ResponseEntity<Species> response = speciesController.getSpecies(speciesId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetSpeciesHandleException() throws Exception { // createSpecies may throw IOException
        String speciesId = "test";
        // When the same id is passed in, our mock Species DAO will return null, simulating
        // no species found

        doThrow(new IOException()).when(mockSpeciesDAO).getSpecies(speciesId);
        // Invoke
        ResponseEntity<Species> response = speciesController.getSpecies(speciesId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all SpeciesController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateSpecies() throws IOException {  // createSpecies may throw IOException
        // Setup
        String[] test = {"one", "two", "three"}; 
        Species species = new Species("mongoose", test);
        // when createSpecies is called, return true simulating successful
        // creation and save
        when(mockSpeciesDAO.createSpecies(species)).thenReturn(species);

        // Invoke
        ResponseEntity<Species> response = speciesController.createSpecies(species);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(species,response.getBody());
    }

    @Test
    public void testCreateSpeciesFailed() throws IOException {  // createSpecies may throw IOException
        // Setup
        String[] test = {"one", "two", "three"}; 
        Species species = new Species("mongoose", test);
        // when createSpecies is called, return false simulating failed
        // creation and save
        when(mockSpeciesDAO.createSpecies(species)).thenReturn(null);

        // Invoke
        ResponseEntity<Species> response = speciesController.createSpecies(species);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateSpeciesHandleException() throws IOException {  // createSpecies may throw IOException
        // Setup
        String[] test = {"one", "two", "three"}; 
        Species species = new Species("mongoose", test);

        // When createSpecies is called on the Mock Species DAO, throw an IOException
        doThrow(new IOException()).when(mockSpeciesDAO).createSpecies(species);

        // Invoke
        ResponseEntity<Species> response = speciesController.createSpecies(species);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateSpecies() throws IOException { // updateSpecies may throw IOException
        // Setup
        String[] test = {"one", "two", "three"}; 
        Species species = new Species("mongoose", test);
        // when updateSpecies is called, return true simulating successful
        // update and save
        when(mockSpeciesDAO.updateSpecies(species)).thenReturn(species);
        ResponseEntity<Species> response = speciesController.updateSpecies(species);
        

        // Invoke
        response = speciesController.updateSpecies(species);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(species,response.getBody());
    }

    @Test
    public void testUpdateSpeciesFailed() throws IOException { // updateSpecies may throw IOException
        // Setup
        String[] test = {"one", "two", "three"}; 
        Species species = new Species("mongoose", test);
        // when updateSpecies is called, return true simulating successful
        // update and save
        when(mockSpeciesDAO.updateSpecies(species)).thenReturn(null);

        // Invoke
        ResponseEntity<Species> response = speciesController.updateSpecies(species);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateSpeciesHandleException() throws IOException { // updateSpecies may throw IOException
        // Setup
        String[] test = {"one", "two", "three"}; 
        Species species = new Species("mongoose", test);
        // When updateSpecies is called on the Mock Species DAO, throw an IOException
        doThrow(new IOException()).when(mockSpeciesDAO).updateSpecies(species);

        // Invoke
        ResponseEntity<Species> response = speciesController.updateSpecies(species);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetSpecieses() throws IOException { // getSpecieses may throw IOException
        // Setup
        Species[] specieses = new Species[2];
        String[] test = {"one", "two", "three"}; 
        specieses[0] = new Species("Bolt",test);
        specieses[1] = new Species("The Great Iguana", test );
        // When getSpecieses is called return the specieses created above
        when(mockSpeciesDAO.getSpeciess()).thenReturn(specieses);

        // Invoke
        ResponseEntity<Species[]> response = speciesController.getSpeciess();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(specieses,response.getBody());
    }

    @Test
    public void testGetSpeciesesHandleException() throws IOException { // getSpecieses may throw IOException
        // Setup
        // When getSpecieses is called on the Mock Species DAO, throw an IOException
        doThrow(new IOException()).when(mockSpeciesDAO).getSpeciess();

        // Invoke
        ResponseEntity<Species[]> response = speciesController.getSpeciess();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchSpecieses() throws IOException { // findSpecieses may throw IOException
        // Setup
        String searchString = "Mon";
        Species[] specieses = new Species[2];
        String[] test = {"one", "two", "three"}; 
        specieses[0] = new Species("Bolt",test);
        specieses[1] = new Species("The Great Iguana", test);
        // When findSpecieses is called with the search string, return the two
        /// specieses above
        when(mockSpeciesDAO.findSpeciess(searchString)).thenReturn(specieses);

        // Invoke
        ResponseEntity<Species[]> response = speciesController.searchSpeciess(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(specieses,response.getBody());
    }

    @Test
    public void testSearchSpeciesesHandleException() throws IOException { // findSpecieses may throw IOException
        // Setup
        String searchString = "an";
        // When createSpecies is called on the Mock Species DAO, throw an IOException
        doThrow(new IOException()).when(mockSpeciesDAO).findSpeciess(searchString);

        // Invoke
        ResponseEntity<Species[]> response = speciesController.searchSpeciess(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteSpecies() throws IOException { // deleteSpecies may throw IOException
        // Setup
        String speciesName = "test";
        // when deleteSpecies is called return true, simulating successful deletion
        when(mockSpeciesDAO.deleteSpecies(speciesName)).thenReturn(true);

        // Invoke
        ResponseEntity<Species> response = speciesController.deleteSpecies(speciesName);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteSpeciesNotFound() throws IOException { // deleteSpecies may throw IOException
        // Setup
        String speciesName = "test";
        // when deleteSpecies is called return true, simulating successful deletion
        when(mockSpeciesDAO.deleteSpecies(speciesName)).thenReturn(true);

        // when deleteSpecies is called return false, simulating failed deletion
        when(mockSpeciesDAO.deleteSpecies(speciesName)).thenReturn(false);

        // Invoke
        ResponseEntity<Species> response = speciesController.deleteSpecies(speciesName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteSpeciesHandleException() throws IOException { // deleteSpecies may throw IOException
        // Setup
        String speciesName = "test";
        // When deleteSpecies is called on the Mock Species DAO, throw an IOException
        doThrow(new IOException()).when(mockSpeciesDAO).deleteSpecies(speciesName);

        // Invoke
        ResponseEntity<Species> response = speciesController.deleteSpecies(speciesName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}