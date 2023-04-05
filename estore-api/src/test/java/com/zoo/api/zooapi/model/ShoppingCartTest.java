package com.zoo.api.zooapi.model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Customer class
 *
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class ShoppingCartTest {
@Test
public void testSC(){
    int expected_id = 10;
    HashSet<Integer> animals = new HashSet<Integer>();
    animals.add(1);
    animals.add(2);
    animals.add(3);
    ShoppingCart shoppingCart = new ShoppingCart(expected_id, animals);
    int[] expected_animals = {1,2,3};

    // Analyze
    assertEquals(expected_id, shoppingCart.getCustomerId());
    assertEquals(expected_animals, shoppingCart.getAnimals());

}

@Test
public void removeAnimal(){
    int expected_id = 10;
    HashSet<Integer> animals = new HashSet<Integer>();
    animals.add(1);
    animals.add(2);
    animals.add(3);
    ShoppingCart shoppingCart = new ShoppingCart(expected_id, animals);
    int[] expected_animals = {2,3};

    boolean expected_bool = true;
    shoppingCart.removeAnimal(1);
    assertEquals( expected_animals, shoppingCart.getAnimals());
}

@Test
public void containsAnimal(){
    int expected_id = 1;
    HashSet<Integer> animals = new HashSet<Integer>();
    animals.add(1);
    animals.add(2);
    animals.add(3);
    ShoppingCart shoppingCart = new ShoppingCart(expected_id, animals);
    int[] expected_animals = {1,2,3};

    boolean expected_bool = true;
    boolean test = shoppingCart.containsAnimal(expected_id);
    assertEquals( expected_bool, test);
}

@Test
public void isEmpty(){
    int expected_id = 1;
    HashSet<Integer> animals = new HashSet<Integer>();
    animals.add(1);
    animals.add(2);
    animals.add(3);
    ShoppingCart shoppingCart = new ShoppingCart(expected_id, animals);
    int[] expected_animals = {};

    boolean expected_bool = false;
    boolean test = shoppingCart.containsAnimal(expected_id);
    assertEquals( expected_animals, shoppingCart.isEmpty());
}

@Test
    public void testToString() {
        int expected_id = 1;
        HashSet<Integer> animals = new HashSet<Integer>();
        animals.add(1);
        animals.add(2);
        animals.add(3);
        ShoppingCart shoppingCart = new ShoppingCart(expected_id, animals);
        int[] expected_animals = {};
        String temp = "customer [id=%d, username=%s]";
        String expected_string = String.format(temp, expected_id, animals);


        // Invoke
        

        // Analyze
        assertEquals(expected_string, shoppingCart.toString());
    }

    
}
