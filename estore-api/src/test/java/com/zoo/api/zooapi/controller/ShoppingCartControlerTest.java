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
     * Before each test, create a new CustomerController object and inject
     * a mock Customer DAO
     */
    @BeforeEach
    public void setupShoppingCartController() {
        mockDAO = mock(ShoppingCartDAO.class);
        test = new ShoppingCartController(mockDAO);
    }

    @Test
    public void addAnimalToShoppingCartTest() throws IOException {  // getCustomer may throw IOException
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
    public void testGetShoppingCart() throws IOException { // getAnimales may throw IOException
        // Setup
        ShoppingCart[] shoppingCarts = new ShoppingCart[2];
        shoppingCarts[0] = new ShoppingCart(10);
        Animal animal1 = new Animal(10,"Joe");

        shoppingCarts[1] = new ShoppingCart(50);
        Animal animal2 = new Animal(50,"The Great Iguana");
        // When getAnimales is called return the animales created above
        when(mockDAO.getShoppingCarts()).thenReturn(shoppingCarts);

        // Invoke
        ResponseEntity<ShoppingCart[]> response = test.getShoppingCart();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(shoppingCarts,response.getBody());
    }

    @Test
    public void testGetShoppingCartHandleException() throws IOException { // getAnimales may throw IOException
        // Setup
        // When getAnimales is called on the Mock Animal DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).getShoppingCarts();

        // Invoke
        ResponseEntity<ShoppingCart[]> response = test.getShoppingCart();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all CustomerController methods
     * are implemented.
     ****************************************************************/

}