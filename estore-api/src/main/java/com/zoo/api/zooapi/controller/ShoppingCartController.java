package com.zoo.api.zooapi.controller;

import com.zoo.api.zooapi.model.Animal;
import com.zoo.api.zooapi.model.Customer;
import com.zoo.api.zooapi.model.ShoppingCart;
import com.zoo.api.zooapi.persistence.CustomerDAO;
import com.zoo.api.zooapi.persistence.ShoppingCartDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("shopppingcart")
public class ShoppingCartController {
    private static final Logger Log = Logger.getLogger(ShoppingCartController.class.getName());
    private ShoppingCartDAO shoppingCartDao;

    public ShoppingCartController(ShoppingCartDAO shoppingCartDao) {
        this.shoppingCartDao = shoppingCartDao;
    }

    @GetMapping("")
    public ResponseEntity<ShoppingCart[]> getShoppingCart() {
        Log.info("GET /shoppingcart");

        try {
            ShoppingCart cart[] = shoppingCartDao.getShoppingCarts();
            return new ResponseEntity<ShoppingCart[]>(cart,HttpStatus.OK);
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCart> addAnimalToShoppingCart(@PathVariable int id, @PathVariable int animalId) {
        Log.info("PUT /shoppingcart " + id);

        // Replace below with your implementation
        try {
            ShoppingCart cartnew = shoppingCartDao.addAnimalToShoppingCart(id, animalId);
            if (cartnew == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                return new ResponseEntity<>(cartnew, HttpStatus.OK);
            }
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ShoppingCart> removeAnimalFromShoppingCart(@PathVariable int id, @PathVariable int animalId) {
        Log.info("DELETE /animal/" + id);

        try {
            ShoppingCart cartnew = shoppingCartDao.removeAnimalFromShoppingCart(id, animalId);
            if (cartnew.containsAnimal(animalId))
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                return new ResponseEntity<>(cartnew, HttpStatus.OK);
            }
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
