package com.zoo.api.zooapi.controller;


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
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import com.zoo.api.zooapi.model.*;

/**
 * Handles the REST API requests for the animal resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Group 6F
 */

@RestController
@RequestMapping("owner")
public class OwnerController {
    private static final Logger LOG = Logger.getLogger(OwnerController.class.getName());
    private OwnerDAO ownerDao;

    public OwnerController(OwnerDAO ownerDao) {
        this.ownerDao = ownerDao;
    }

/**
 * following mappings are subject to change
 */

    @GetMapping("/animal/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable int id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("/animal/{id}")
    public ResponseEntity<Animal> addAnimal(@RequestBody Animal animal) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("animal")
    public ResponseEntity<Animal> editAnimal(@RequestBody Animal animal) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/animal/{id}")
    public ResponseEntity<Animal> removeAnimal(@PathVariable Animal animal) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("customer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}