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
public class ShoppingCartFileDAO implements ShoppingCartDAO {
    private static final Logger LOG = Logger.getLogger(ShoppingCartFileDAO.class.getName());

    Map<Integer, ShoppingCart> shoppingCarts;

    private final HashMap<Integer, Integer> animalCheckoutCount;

    private final ObjectMapper objectMapper;
    private final String filename;

    /**
     * Creates a new ShoppingCartFileDAO
     * @param filename the file to load the shopping carts from
     * @param objectMapper the object mapper to use
     */
    public ShoppingCartFileDAO(@Value("data/shoppingcart.json") String filename, ObjectMapper objectMapper) {
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

    /**
     * Loads the shopping carts from the file
     * @throws IOException if there is an error reading the file
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCart[] getShoppingCarts() throws IOException {
        return shoppingCarts.values().toArray(new ShoppingCart[0]);
    }

    /**
     * {@inheritDoc}
     */
    public ShoppingCart getShoppingCart(int customerId) {
        if (shoppingCarts.containsKey(customerId)) {
            return shoppingCarts.get(customerId);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    public ShoppingCart checkoutShoppingCart(int id) {
        if (!shoppingCarts.containsKey(id)) {
            return null;
        }
        ShoppingCart shoppingCart = shoppingCarts.get(id);
        for (int animalId : shoppingCart.getAnimals()) {
            decrementAnimal(animalId);
        }
        shoppingCart.clearAnimals();
        return shoppingCart;
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteShoppingCart(int id) {
        if (!shoppingCarts.containsKey(id)) {
            return false;
        }
        shoppingCarts.remove(id);
        return true;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    private void incrementAnimal(int id) {
        if (animalCheckoutCount.containsKey(id)) {
            animalCheckoutCount.put(id, animalCheckoutCount.get(id) + 1);
        } else {
            animalCheckoutCount.put(id, 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    private void decrementAnimal(int id) {
        if (animalCheckoutCount.containsKey(id)) {
            animalCheckoutCount.put(id, animalCheckoutCount.get(id) - 1);
        } else {
            // This should never happen, but just in case
            animalCheckoutCount.put(id, 0);
        }
    }
}
