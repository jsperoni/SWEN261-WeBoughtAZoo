package com.zoo.api.zooapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.logging.Logger;

/**
 * Represents a shopping cart entity
 *
 * @author Group 6F
 */
public class ShoppingCart {
    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());

    @JsonProperty("customer_id") private int customerId;
    @JsonProperty("animals") private HashSet<Integer> animals;

    /**
     * Create a shopping cart with the following information
     * @param customerId id of the customer
     * @param animals animals in the shopping cart
     */
    public ShoppingCart(@JsonProperty("customer_id") int customerId, @JsonProperty("animals") HashSet<Integer> animals) {
        this.customerId = customerId;
        this.animals = animals;
    }

    /**
     * Create a shopping cart with the following information
     * @param customerId id of the customer
     */
    public ShoppingCart(int customerId) {
        this(customerId, new HashSet<>());
    }

    /**
     * Retrieves the id of the customer
     * @return The id of the customer
     */
    public int getCustomerId() {return customerId;}

    /**
     * Gets the animals in the shopping cart
     * @return animals in the shopping cart
     */
    public int[] getAnimals() {
        int[] animals = new int[this.animals.size()];
        int i = 0;
        for (int animalId : this.animals) {
            animals[i++] = animalId;
        }
        return animals;
    }

    /**
     * Adds an animal to the shopping cart
     * @param animalId id of the animal
     */
    public void addAnimal(int animalId) {
        this.animals.add(animalId);
    }

    /**
     * Removes an animal from the shopping cart
     * @param animalId id of the animal
     * @return true if the animal was removed, false otherwise
     */
    public boolean removeAnimal(int animalId) {
        if (!this.animals.contains(animalId)) {
            return false;
        }
        this.animals.remove(animalId);
        return true;
    }

    /**
     * Checks if the shopping cart contains an animal
     * @param animalId id of the animal
     * @return true if the animal is in the shopping cart, false otherwise
     */
    public boolean containsAnimal(int animalId) {
        return this.animals.contains(animalId);
    }

    /**
     * Clears the animals in the shopping cart
     */
    public void clearAnimals() {
        this.animals.clear();
    }

    /**
     * Checks if the shopping cart is empty
     * @return true if the shopping cart is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.animals.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("shopping_cart [customer_id=%d, animals=%s]",customerId,animals);
    }
}

