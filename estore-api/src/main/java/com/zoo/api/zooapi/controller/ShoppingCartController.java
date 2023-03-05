package com.zoo.api.zooapi.controller;

import com.zoo.api.zooapi.model.Animal;
import com.zoo.api.zooapi.model.Customer;
import com.zoo.api.zooapi.persistence.CustomerDAO;
import com.zoo.api.zooapi.persistence.ShoppingCartDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("customers")
public class ShoppingCartController {
    private static final Logger Log = Logger.getLogger(ShoppingCartController.class.getName());
    private ShoppingCartDAO shoppingCartDao;

    public ShoppingCartController(ShoppingCartDAO mockDAO) {
        this.shoppingCartDao = shoppingCartDao;
    }

    @GetMapping("")
    public ResponseEntity<Customer> getShoppingCart() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    

    @PutMapping("/{id}")
    public ResponseEntity<Customer> addToShoppingCart(@PathVariable int id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> removeFromShoppingCart(@PathVariable int id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
