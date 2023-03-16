package com.zoo.api.zooapi.controller;

import com.zoo.api.zooapi.persistence.AnimalDAO;
import com.zoo.api.zooapi.persistence.OwnerDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zoo.api.zooapi.model.*;
import com.zoo.api.zooapi.controller.*;

/**
 * Handles the REST API requests for the animal resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("owner")
public class OwnerController {
    private static final Logger LOG = Logger.getLogger(OwnerController.class.getName());
    private OwnerDAO OwnerDao;

    public OwnerController(OwnerDAO ownerDao) {
        this.OwnerDao = ownerDao;
    }

/**
 * owner needs to get indvl animals, all animals, edit the animals
 * get cust, edit cust
 */

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable int id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Animal> addAnimal(@PathVariable Animal animal) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("")
    public ResponseEntity<Animal> editAnimal(@PathVariable Animal animal) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Animal> removeAnimal(@PathVariable Animal animal) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@PathVariable Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}