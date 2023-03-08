package com.zoo.api.zooapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.zoo.api.zooapi.persistence.ShoppingCartDAO;
import com.zoo.api.zooapi.model.Animal;
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
    public void testGetCustomerNotFound() throws Exception { // createCustomer may throw IOException
        // Setup
        int customerId = 99;
        // When the same id is passed in, our mock Customer DAO will return null, simulating
        // no customer found
        when(mockCustomerDAO.getCustomer(customerId)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetCustomerHandleException() throws Exception { // createCustomer may throw IOException
        // Setup
        int customerId = 99;
        // When getCustomer is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).getCustomer(customerId);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all CustomerController methods
     * are implemented.
     ****************************************************************/

}