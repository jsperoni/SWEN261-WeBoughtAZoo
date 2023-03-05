package com.zoo.api.zooapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.api.zooapi.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ShoppingCartFileDAO {
    private static final Logger LOG = Logger.getLogger(ShoppingCartFileDAO.class.getName());

    private ObjectMapper objectMapper;
    private String filename;

public ShoppingCartFileDAO(@Value("data/shopping_carts.json") String filename, ObjectMapper objectMapper) {
        this.filename = filename;
        this.objectMapper = objectMapper;
    }

    public ShoppingCart getShoppingCart(int customerId) {
        return null;
    }

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return null;
    }

    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        return null;
    }

    public ShoppingCart addAnimalToShoppingCart(int id, int animalId) {
        return null;
    }

    public ShoppingCart removeAnimalFromShoppingCart(int id, int animalId) {
        return null;
    }

    public boolean deleteShoppingCart(int id) {
        return false;
    }

    public int[] getCheckedOutAnimals() {
        return new int[0];
    }
}
