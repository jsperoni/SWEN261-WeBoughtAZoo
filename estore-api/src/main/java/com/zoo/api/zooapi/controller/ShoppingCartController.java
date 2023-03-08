package com.zoo.api.zooapi.controller;

import com.zoo.api.zooapi.model.Animal;
import com.zoo.api.zooapi.model.Customer;
import com.zoo.api.zooapi.model.ShoppingCart;
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
    public ResponseEntity<ShoppingCart[]> getShoppingCart() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCart> addAnimalToShoppingCart(@PathVariable int id, @PathVariable int animalId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> removeShoppingCart(@PathVariable int id, @PathVariable int animalId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
