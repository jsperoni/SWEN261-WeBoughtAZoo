package com.zoo.api.zooapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.api.zooapi.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class ShoppingCartFileDAO {
    private static final Logger LOG = Logger.getLogger(ShoppingCartFileDAO.class.getName());

    private Map<Integer, ShoppingCart> shoppingCarts;

    private final HashMap<Integer, Integer> animalCheckoutCount;

    private final ObjectMapper objectMapper;
    private final String filename;

    public ShoppingCartFileDAO(@Value("data/shopping_carts.json") String filename, ObjectMapper objectMapper) {
        this.filename = filename;
        this.objectMapper = objectMapper;
        animalCheckoutCount = new HashMap<>();
        try {
            load();  // load the Animals from the file
        } catch (IOException e) {
            LOG.severe("Unable to load shopping carts from file: " + filename + " " + e.getMessage());
            shoppingCarts = new HashMap<>();
        }
    }

    private void load() throws IOException {
        shoppingCarts = new HashMap<>();

        ShoppingCart[] carts = objectMapper.readValue(new File(filename), ShoppingCart[].class);

        for (ShoppingCart cart : carts) {
            shoppingCarts.put(cart.getCustomerId(), cart);
            for (int animalId : cart.getAnimals()) {
                incrementAnimal(animalId);
            }
        }
    }

    public ShoppingCart getShoppingCart(int customerId) {
        if (shoppingCarts.containsKey(customerId)) {
            return shoppingCarts.get(customerId);
        }
        return null;
    }

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        if (shoppingCarts.containsKey(shoppingCart.getCustomerId())) {
            return shoppingCarts.get(shoppingCart.getCustomerId());
        }
        for (int animalId : shoppingCart.getAnimals()) {
            incrementAnimal(animalId);
        }
        shoppingCarts.put(shoppingCart.getCustomerId(), shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        if (!shoppingCarts.containsKey(shoppingCart.getCustomerId())) {
            return createShoppingCart(shoppingCart);
        }

        for (int animalId : shoppingCarts.get(shoppingCart.getCustomerId()).getAnimals()) {
            decrementAnimal(animalId);
        }
        for (int animalId : shoppingCart.getAnimals()) {
            incrementAnimal(animalId);
        }

        shoppingCarts.put(shoppingCart.getCustomerId(), shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart addAnimalToShoppingCart(int id, int animalId) {
        if (!shoppingCarts.containsKey(id)) {
            createShoppingCart(new ShoppingCart(id));
        }
        ShoppingCart shoppingCart = shoppingCarts.get(id);
        if (!shoppingCart.containsAnimal(animalId)) {
            shoppingCart.addAnimal(animalId);
            incrementAnimal(animalId);
        }
        return shoppingCart;
    }

    public ShoppingCart removeAnimalFromShoppingCart(int id, int animalId) {
        if (!shoppingCarts.containsKey(id)) {
            return null;
        }
        ShoppingCart shoppingCart = shoppingCarts.get(id);
        if (shoppingCart.containsAnimal(animalId)) {
            shoppingCart.removeAnimal(animalId);
            decrementAnimal(animalId);
        }
        return shoppingCart;
    }

    public boolean deleteShoppingCart(int id) {
        if (!shoppingCarts.containsKey(id)) {
            return false;
        }
        shoppingCarts.remove(id);
        return true;
    }

    public int[] getCheckedOutAnimals() {
        HashSet<Integer> checkedOutAnimals = new HashSet<>();
        for(Map.Entry<Integer, Integer> entry : animalCheckoutCount.entrySet()) {
            if (entry.getValue() > 0) {
                checkedOutAnimals.add(entry.getKey());
            }
        }
        int[] animals = new int[checkedOutAnimals.size()];
        int i = 0;
        for (int animalId : checkedOutAnimals) {
            animals[i++] = animalId;
        }
        return animals;
    }

    private void incrementAnimal(int id) {
        if (animalCheckoutCount.containsKey(id)) {
            animalCheckoutCount.put(id, animalCheckoutCount.get(id) + 1);
        } else {
            animalCheckoutCount.put(id, 1);
        }
    }

    private void decrementAnimal(int id) {
        if (animalCheckoutCount.containsKey(id)) {
            animalCheckoutCount.put(id, animalCheckoutCount.get(id) - 1);
        } else {
            // This should never happen, but just in case
            animalCheckoutCount.put(id, 0);
        }
    }
}
