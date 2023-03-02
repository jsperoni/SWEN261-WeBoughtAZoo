package com.zoo.api.zooapi.controller;

import com.zoo.api.zooapi.model.Customer;
import com.zoo.api.zooapi.persistence.CustomerDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private static final Logger Log = Logger.getLogger(CustomerController.class.getName());
    private CustomerDAO customerDao;

    public CustomerController(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("")
    public ResponseEntity<Customer[]> getCustomers() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/")
    public ResponseEntity<Customer[]> searchCustomers(@RequestParam String containsText) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable int id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
