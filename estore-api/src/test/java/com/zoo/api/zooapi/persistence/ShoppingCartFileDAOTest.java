package com.zoo.api.zooapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.api.zooapi.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Persistence-tier")
public class ShoppingCartFileDAOTest {
   ShoppingCartFileDAO shoppingCartFileDAO;

   ShoppingCart[] testShoppingCarts;

   ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupShoppingCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testShoppingCarts = new ShoppingCart[3];
        testShoppingCarts[0] = new ShoppingCart(9, new HashSet<>());
        testShoppingCarts[0].addAnimal(0);
        testShoppingCarts[0].addAnimal(1);
        testShoppingCarts[0].addAnimal(2);

        testShoppingCarts[1] = new ShoppingCart(39, new HashSet<>());
        testShoppingCarts[1].addAnimal(3);
        testShoppingCarts[1].addAnimal(4);
        testShoppingCarts[1].addAnimal(5);

        HashSet<Integer> testSet = new HashSet<Integer>();
        testSet.add(0);
        testSet.add(3);
        testSet.add(6);
        testShoppingCarts[2] = new ShoppingCart(45, testSet);

        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), ShoppingCart[].class))
                .thenReturn(testShoppingCarts);
        shoppingCartFileDAO = new ShoppingCartFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetShoppingCart() {
        // Invoke
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(9);

        // Analyze
        assertEquals(9, shoppingCart.getCustomerId());
        assertEquals(3, shoppingCart.getAnimals().length);
    }

    @Test
    public void testGetShoppingCartNotFound() {
        // Invoke
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(100);

        // Analyze
        assertNull(shoppingCart);
    }

    @Test
    public void testAddShoppingCart() {
        // Invoke
        shoppingCartFileDAO.createShoppingCart(new ShoppingCart(100));

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(100);
        assertEquals(100, shoppingCart.getCustomerId());
        assertEquals(0, shoppingCart.getAnimals().length);
    }

    @Test
    public void testAddShoppingCartFilled() {
        HashSet<Integer> testSet = new HashSet<>();
        testSet.add(1);
        testSet.add(4);
        testSet.add(7);
        testSet.add(10);

        // Invoke
        shoppingCartFileDAO.createShoppingCart(new ShoppingCart(100, testSet));

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(100);
        assertEquals(100, shoppingCart.getCustomerId());
        assertEquals(4, shoppingCart.getAnimals().length);
    }

    @Test
    public void testAddShoppingCartAlreadyExists() {
        // Invoke
        shoppingCartFileDAO.createShoppingCart(new ShoppingCart(9));

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(9);
        assertEquals(9, shoppingCart.getCustomerId());
        assertEquals(3, shoppingCart.getAnimals().length);
    }

    @Test
    public void testUpdateShoppingCart() {
        // Invoke
        shoppingCartFileDAO.updateShoppingCart(new ShoppingCart(9, new HashSet<>()));

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(9);
        assertEquals(9, shoppingCart.getCustomerId());
        assertEquals(0, shoppingCart.getAnimals().length);
    }

    @Test
    public void testUpdateShoppingCartFilled() {
        HashSet<Integer> testSet = new HashSet<>();
        testSet.add(1);
        testSet.add(4);
        testSet.add(7);
        testSet.add(10);

        // Invoke
        shoppingCartFileDAO.updateShoppingCart(new ShoppingCart(45, testSet));

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(45);
        assertEquals(45, shoppingCart.getCustomerId());
        assertEquals(4, shoppingCart.getAnimals().length);
    }

    @Test
    public void testUpdateShoppingCartDoesntExist() {
        // Invoke
        shoppingCartFileDAO.updateShoppingCart(new ShoppingCart(100, new HashSet<>()));

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(100);
        assertEquals(100, shoppingCart.getCustomerId());
        assertEquals(0, shoppingCart.getAnimals().length);
    }

    @Test
    public void testAddAnimalToShoppingCart() {
        // Invoke
        shoppingCartFileDAO.addAnimalToShoppingCart(9, 7);

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(9);
        assertEquals(9, shoppingCart.getCustomerId());
        assertEquals(4, shoppingCart.getAnimals().length);
    }

    @Test
    public void testAddAnimalToShoppingCartDoesntExist() {
        // Invoke
        shoppingCartFileDAO.addAnimalToShoppingCart(100, 7);

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(100);
        assertEquals(100, shoppingCart.getCustomerId());
        assertEquals(1, shoppingCart.getAnimals().length);
    }

    @Test
    public void testRemoveAnimalFromShoppingCart() {
        // Invoke
        shoppingCartFileDAO.removeAnimalFromShoppingCart(9, 0);

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(9);
        assertEquals(9, shoppingCart.getCustomerId());
        assertEquals(2, shoppingCart.getAnimals().length);
    }

    @Test
    public void testRemoveAnimalFromShoppingCartDoesntExist() {
        // Invoke
        ShoppingCart shoppingCart = shoppingCartFileDAO.removeAnimalFromShoppingCart(100, 0);

        // Analyze
        assertNull(shoppingCart);
    }

    @Test
    public void testRemoveAnimalFromShoppingCartAnimalDoesntExist() {
        // Invoke
        shoppingCartFileDAO.removeAnimalFromShoppingCart(9, 100);

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(9);
        assertEquals(9, shoppingCart.getCustomerId());
        assertEquals(3, shoppingCart.getAnimals().length);
    }

    @Test
    public void testRemoveShoppingCart() {
        // Invoke
        boolean removed = shoppingCartFileDAO.deleteShoppingCart(9);

        // Analyze
        assertTrue(removed);
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(9);
        assertNull(shoppingCart);
    }

    @Test
    public void testRemoveShoppingCartDoesntExist() {
        // Invoke
        boolean removed = shoppingCartFileDAO.deleteShoppingCart(100);

        // Analyze
        assertFalse(removed);
    }

    @Test
    public void testGetCheckedOutAnimals() {
        // Invoke
        int[] animals = shoppingCartFileDAO.getCheckedOutAnimals();

        // Analyze
        assertEquals(7, animals.length);
    }

    @Test
    public void testLoadException() throws IOException {
        // Setup
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), ShoppingCart[].class))
                .thenThrow(new IOException());

        // Invoke
        shoppingCartFileDAO = new ShoppingCartFileDAO("doesnt_matter.txt",mockObjectMapper);

        // Analyze
        ShoppingCart shoppingCart = shoppingCartFileDAO.getShoppingCart(9);
        assertNull(shoppingCart);
    }
}
