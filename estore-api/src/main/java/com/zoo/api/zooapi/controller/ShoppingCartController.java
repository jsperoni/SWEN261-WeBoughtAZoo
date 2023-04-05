package com.zoo.api.zooapi.controller;

import com.zoo.api.zooapi.model.ShoppingCart;
import com.zoo.api.zooapi.persistence.ShoppingCartDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("shoppingcart")
public class ShoppingCartController {
    private static final Logger Log = Logger.getLogger(ShoppingCartController.class.getName());
    private ShoppingCartDAO shoppingCartDao;

    /**
     * Constructor for ShoppingCartController. Takes in a ShoppingCartDAO object.
     * @param shoppingCartDao ShoppingCartDAO object to be used by the controller. Accepts any object implementing the ShoppingCartDAO interface.
     */
    public ShoppingCartController(ShoppingCartDAO shoppingCartDao) {
        this.shoppingCartDao = shoppingCartDao;
    }

    /**
     * GET /shoppingcart - Returns all shopping carts.
     * @return A ResponseEntity containing an array of all shopping carts.
     */
    @GetMapping("")
    public ResponseEntity<ShoppingCart[]> getShoppingCarts() {
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

    /**
     * GET /shoppingcart/{id} - Returns a shopping cart with the given id.
     * @param id The id of the shopping cart to be returned.
     * @return A ResponseEntity containing the shopping cart with the given id, or a 404 if no shopping cart with the given id exists.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart[]> getShoppingCart(@PathVariable int id) {
        Log.info("GET /shoppingcart" + id);

        try {
            ShoppingCart cart[] = shoppingCartDao.getShoppingCarts();
            return new ResponseEntity<ShoppingCart[]>(cart,HttpStatus.OK);
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * POST /shoppingcart - Creates a new shopping cart.
     * @param cart The shopping cart to be created.
     * @return A ResponseEntity containing the newly created shopping cart.
     */
    @PutMapping("/{id}/{animalId}")
    public ResponseEntity<ShoppingCart> addAnimalToShoppingCart(@PathVariable int id, @PathVariable int animalId) {
        Log.info("PUT /shoppingcart " + id);

        // Replace below with your implementation
        try {
            ShoppingCart cartnew = shoppingCartDao.addAnimalToShoppingCart(id, animalId);
            if (cartnew == null){
                cartnew = shoppingCartDao.createShoppingCart(new ShoppingCart(id));
                shoppingCartDao.addAnimalToShoppingCart(id, animalId);
                return new ResponseEntity<>(cartnew, HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<>(cartnew, HttpStatus.OK);
            }
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE /shoppingcart/{id} - Deletes a shopping cart with the given id.
     * @param id The id of the shopping cart to be deleted.
     * @return A ResponseEntity containing the deleted shopping cart, or a 404 if no shopping cart with the given id exists.
     */
    @DeleteMapping("/{id}/{animalId}")
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

    /**
     * POST /shoppingcart/checkout/{id} - Checks out a shopping cart with the given id.
     * @param id The id of the shopping cart to be checked out.
     * @return A ResponseEntity containing the checked out shopping cart, or a 404 if no shopping cart with the given id exists.
     */
    @PostMapping("/checkout/{id}")
    public ResponseEntity<ShoppingCart> checkoutShoppingCart(@PathVariable int id) {
        Log.info("POST /shoppingcart/checkout/" + id);

        try {
            ShoppingCart cartnew = shoppingCartDao.checkoutShoppingCart(id);
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
}
