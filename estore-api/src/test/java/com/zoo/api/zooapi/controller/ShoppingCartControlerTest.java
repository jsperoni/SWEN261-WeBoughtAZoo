package com.zoo.api.zooapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.zoo.api.zooapi.persistence.ShoppingCartDAO;
import com.zoo.api.zooapi.model.Animal;
import com.zoo.api.zooapi.model.Customer;
import com.zoo.api.zooapi.model.ShoppingCart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Shopping Cart  Controller class
 *
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class ShoppingCartControlerTest {
    private ShoppingCartController test;
    private ShoppingCartDAO mockDAO;

    /**
     * Before each test, create a new ShoppingCartController object and inject
     * a mock Shopping cart DAO
     */
    @BeforeEach
    public void setupShoppingCartController() {
        mockDAO = mock(ShoppingCartDAO.class);
        test = new ShoppingCartController(mockDAO);
    }

    @Test
    public void testAddAnimalToShoppingCart() throws IOException {  
        // Setup
        ShoppingCart shoppingCart = new ShoppingCart(20);
        Animal animal = new Animal(10, "joe");
        when(mockDAO.addAnimalToShoppingCart(shoppingCart.getCustomerId(), animal.getId())).thenReturn(shoppingCart);

        // Invoke
        ResponseEntity<ShoppingCart> response = test.addAnimalToShoppingCart(shoppingCart.getCustomerId(),animal.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(shoppingCart,response.getBody());
    }

    @Test
    public void testAddAnimalToShoppingCartNotFound() throws Exception { 
        // Setup
        ShoppingCart shoppingCart = new ShoppingCart(20);
        Animal animal = new Animal(10, "joe");
        when(mockDAO.addAnimalToShoppingCart(shoppingCart.getCustomerId(), animal.getId())).thenReturn(null);

        ResponseEntity<ShoppingCart> response = test.addAnimalToShoppingCart(shoppingCart.getCustomerId(),animal.getId());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testAddAnimalToShoppingCartHandleException() throws Exception { 
        // Setup
        ShoppingCart shoppingCart = new ShoppingCart(20);
        Animal animal = new Animal(10, "joe");
        doThrow(new IOException()).when(mockDAO.addAnimalToShoppingCart(shoppingCart.getCustomerId(), animal.getId()));;

        // Invoke
        ResponseEntity<ShoppingCart> response = test.addAnimalToShoppingCart(shoppingCart.getCustomerId(),animal.getId());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testRemoveAnimalFromShoppingCart() throws IOException {  // getCustomer may throw IOException
        // Setup
        ShoppingCart shoppingCart = new ShoppingCart(20);
        Animal animal = new Animal(10, "joe");
        mockDAO.addAnimalToShoppingCart(20, 10);
        when(mockDAO.removeAnimalFromShoppingCart(shoppingCart.getCustomerId(), animal.getId())).thenReturn(shoppingCart);

        // Invoke
        ResponseEntity<ShoppingCart> response = test.removeAnimalFromShoppingCart(10, 20);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(shoppingCart,response.getBody());
    }

    @Test
    public void testRemoveAnimalFromShoppingCartNotFound() throws Exception { // createCustomer may throw IOException
        // Setup
        ShoppingCart shoppingCart = new ShoppingCart(20);
        Animal animal = new Animal(10, "joe");
        mockDAO.addAnimalToShoppingCart(20, 10);
        when(mockDAO.addAnimalToShoppingCart(shoppingCart.getCustomerId(), animal.getId())).thenReturn(null);

        ResponseEntity<ShoppingCart> response = test.addAnimalToShoppingCart(shoppingCart.getCustomerId(),animal.getId());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testRemoveAnimalFromShoppingCartHandleException() throws Exception { // createCustomer may throw IOException
        // Setup
        ShoppingCart shoppingCart = new ShoppingCart(20);
        Animal animal = new Animal(10, "joe");
        mockDAO.addAnimalToShoppingCart(20, 10);
        doThrow(new IOException()).when(mockDAO.addAnimalToShoppingCart(shoppingCart.getCustomerId(), animal.getId()));;

        // Invoke
        ResponseEntity<ShoppingCart> response = test.addAnimalToShoppingCart(shoppingCart.getCustomerId(),animal.getId());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
        
    /*****************************************************************
     * The following tests will fail until all CustomerController methods
     * are implemented.
     ****************************************************************/

        // Analyze
    
    }
