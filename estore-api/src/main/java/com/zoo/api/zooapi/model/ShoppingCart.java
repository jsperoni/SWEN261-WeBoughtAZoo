package com.zoo.api.zooapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.logging.Logger;

public class ShoppingCart {
    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());

    @JsonProperty("customer_id") private int customerId;
    @JsonProperty("animals") private HashSet<Integer> animals;

    public ShoppingCart(@JsonProperty("customer_id") int customerId, @JsonProperty("animals") HashSet<Integer> animals) {
        this.customerId = customerId;
        this.animals = animals;
    }

    public ShoppingCart(int customerId) {
        this(customerId, new HashSet<>());
    }

    public int getCustomerId() {return customerId;}

    public int[] getAnimals() {
        int[] animals = new int[this.animals.size()];
        int i = 0;
        for (int animalId : this.animals) {
            animals[i++] = animalId;
        }
        return animals;
    }

    public void addAnimal(int animalId) {
        this.animals.add(animalId);
    }

    public boolean removeAnimal(int animalId) {
        if (!this.animals.contains(animalId)) {
            return false;
        }
        this.animals.remove(animalId);
        return true;
    }

    public boolean containsAnimal(int animalId) {
        return this.animals.contains(animalId);
    }

    public void clearAnimals() {
        this.animals.clear();
    }

    public boolean isEmpty() {
        return this.animals.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("shopping_cart [customer_id=%d, animals=%s]",customerId,animals);
    }
}

