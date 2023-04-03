package com.zoo.api.zooapi.controller;

import com.zoo.api.zooapi.model.Species;
import com.zoo.api.zooapi.persistence.SpeciesDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for the Species resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Group 6F
 */

@RestController
@RequestMapping("Species")
public class SpeciesController {
    private static final Logger LOG = Logger.getLogger(SpeciesController.class.getName());
    private SpeciesDAO SpeciesDao;

    /**
     * Creates a REST API controller to reponds to requests
     *
     * @param SpeciesDao The {@link SpeciesDAO Species Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public SpeciesController(SpeciesDAO SpeciesDao) {
        this.SpeciesDao = SpeciesDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Species Species} for the given id
     * 
     * @param id The id used to locate the {@link Species Species}
     * 
     * @return ResponseEntity with {@link Species Species} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{name}")
    public ResponseEntity<Species> getSpecies(@PathVariable String name) {
        LOG.info("GET /Species/" + name);
        try {
            Species Species = SpeciesDao.getSpecies(name);
            if (Species != null)
                return new ResponseEntity<Species>(Species,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Species Species}
     * 
     * @return ResponseEntity with array of {@link Species Species} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Species[]> getSpeciess() {
        LOG.info("GET /Species");

        try {
            Species Species[] = SpeciesDao.getSpeciess();
            return new ResponseEntity<Species[]>(Species,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }


    /**
     * Responds to the GET request for all {@linkplain Species Species} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Species Species}
     * 
     * @return ResponseEntity with array of {@link Species Species} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all Species that contain the text "ma"
     * GET http://localhost:8080/Species/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Species[]> searchSpeciess(@RequestParam String name) {
        LOG.info("GET /Species/?name="+name);

        try{
            Species Speciesarray[] = SpeciesDao.findSpeciess(name);
            return new ResponseEntity<Species[]>(Speciesarray, HttpStatus.OK);
        } 
        catch(IOException e){
        LOG.log(Level.SEVERE,e.getLocalizedMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }
    /**
     * Creates a {@linkplain Species Species} with the provided Species object
     * 
     * @param Species - The {@link Species Species} to create
     * 
     * @return ResponseEntity with created {@link Species Species} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Species Species} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Species> createSpecies(@RequestBody Species Species) {
        LOG.info("POST /Species " + Species);

        // Replace below with your implementation
        try {
            if(SpeciesDao.getSpecies(Species.getName()) == null){
                Species SpeciesNew = SpeciesDao.createSpecies(Species);
                if (SpeciesNew != null) {
                    return new ResponseEntity<Species>(SpeciesNew,HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<Species>(HttpStatus.CONFLICT);
                }
            } else{
                return new ResponseEntity<Species>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Species Species} with the provided {@linkplain Species Species} object, if it exists
     * 
     * @param Species The {@link Species Species} to update
     * 
     * @return ResponseEntity with updated {@link Species Species} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP staus of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Species> updateSpecies(@RequestBody Species Species) {
        LOG.info("PUT /Species " + Species);

        // Replace below with your implementation
        try {
            Species SpeciesNew = SpeciesDao.updateSpecies(Species);
            if (SpeciesNew == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                return new ResponseEntity<>(SpeciesNew, HttpStatus.OK);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Species Species} with the given id
     * 
     * @param id The id of the {@link Species Species} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Species> deleteSpecies(@PathVariable String name) {
        LOG.info("DELETE /Species/" + name);

        try {

            if (SpeciesDao.deleteSpecies(name))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    }

