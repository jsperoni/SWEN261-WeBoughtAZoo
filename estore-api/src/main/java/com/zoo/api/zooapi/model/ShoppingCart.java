package com.zoo.api.zooapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ShoppingCart {
    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());

    @JsonProperty("customer_id") private int customerId;
    @JsonProperty("animals") private ArrayList<Integer> animals;

    public ShoppingCart(@JsonProperty("customer_id") int customerId, @JsonProperty("animals") ArrayList<Integer> animals) {
        this.customerId = customerId;
        this.animals = animals;
    }

    public int getCustomerId() {return customerId;}

    public void setCustomerId(int customerId) {this.customerId = customerId;}

    public int[] getAnimals() {
        int[] animals = new int[this.animals.size()];
        for (int i = 0; i < this.animals.size(); i++) {
            animals[i] = this.animals.get(i);
        }
        return animals;
    }

    public void addAnimal(int animalId) {
        this.animals.add(animalId);
    }

    public boolean removeAnimal(int animalId) {
        int index = this.animals.indexOf(animalId);
        if (index == -1) {
            return false;
        }
        this.animals.remove(index);
        return true;
    }

    @Override
    public String toString() {
        return String.format("shopping_cart [customer_id=%d, animals=%s]",customerId,animals);
    }
}

