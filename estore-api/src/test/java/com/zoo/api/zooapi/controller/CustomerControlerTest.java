package com.zoo.api.zooapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.zoo.api.zooapi.persistence.CustomerDAO;
import com.zoo.api.zooapi.model.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Customer Controller class
 *
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class CustomerControlerTest {
    private CustomerController customerController;
    private CustomerDAO mockCustomerDAO;

    /**
     * Before each test, create a new CustomerController object and inject
     * a mock Customer DAO
     */
    @BeforeEach
    public void setupCustomerController() {
        mockCustomerDAO = mock(CustomerDAO.class);
        customerController = new CustomerController(mockCustomerDAO);
    }

    @Test
    public void testGetCustomer() throws IOException {  // getCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Galactic-Agent","password");
        // When the same id is passed in, our mock Customer DAO will return the Customer object
        when(mockCustomerDAO.getCustomer(customer.getId())).thenReturn(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomer(customer.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customer,response.getBody());
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

    @Test
    public void testCreateCustomer() throws IOException {  // createCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Wi-Fire","password");
        // when createCustomer is called, return true simulating successful
        // creation and save
        when(mockCustomerDAO.createCustomer(customer)).thenReturn(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(customer,response.getBody());
    }

    @Test
    public void testCreateCustomerFailed() throws IOException {  // createCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Bolt","password");
        // when createCustomer is called, return false simulating failed
        // creation and save
        when(mockCustomerDAO.createCustomer(customer)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateCustomerHandleException() throws IOException {  // createCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Ice Gladiator","password");

        // When createCustomer is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).createCustomer(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() throws IOException { // updateCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Wi-Fire","password");
        // when updateCustomer is called, return true simulating successful
        // update and save
        when(mockCustomerDAO.updateCustomer(customer)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);
        customer.setUsername("Bolt");

        // Invoke
        response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customer,response.getBody());
    }

    @Test
    public void testUpdateCustomerFailed() throws IOException { // updateCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Galactic Agent","password");
        // when updateCustomer is called, return true simulating successful
        // update and save
        when(mockCustomerDAO.updateCustomer(customer)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateCustomerHandleException() throws IOException { // updateCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Galactic-Agent","password");
        // When updateCustomer is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).updateCustomer(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetCustomeres() throws IOException { // getCustomeres may throw IOException
        // Setup
        Customer[] customers = new Customer[2];
        customers[0] = new Customer(99,"Bolt","password");
        customers[1] = new Customer(100,"The-Great-Iguana","password");
        // When getCustomeres is called return the customers created above
        when(mockCustomerDAO.getCustomers()).thenReturn(customers);

        // Invoke
        ResponseEntity<Customer[]> response = customerController.getCustomers();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customers,response.getBody());
    }

    @Test
    public void testGetCustomeresHandleException() throws IOException { // getCustomeres may throw IOException
        // Setup
        // When getCustomeres is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).getCustomers();

        // Invoke
        ResponseEntity<Customer[]> response = customerController.getCustomers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchCustomeres() throws IOException { // findCustomeres may throw IOException
        // Setup
        String searchString = "Jo";
        Customer[] customers = new Customer[2];
        customers[0] = new Customer(99,"Galactic-Agent","password");
        customers[1] = new Customer(100,"Ice Gladiator","password");
        // When findCustomeres is called with the search string, return the two
        /// customers above
        when(mockCustomerDAO.findCustomers(searchString)).thenReturn(customers);

        // Invoke
        ResponseEntity<Customer[]> response = customerController.searchCustomers(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customers,response.getBody());
    }

    @Test
    public void testSearchCustomeresHandleException() throws IOException { // findCustomeres may throw IOException
        // Setup
        String searchString = "an";
        // When createCustomer is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).findCustomers(searchString);

        // Invoke
        ResponseEntity<Customer[]> response = customerController.searchCustomers(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteCustomer() throws IOException { // deleteCustomer may throw IOException
        // Setup
        int customerId = 99;
        // when deleteCustomer is called return true, simulating successful deletion
        when(mockCustomerDAO.deleteCustomer(customerId)).thenReturn(true);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerNotFound() throws IOException { // deleteCustomer may throw IOException
        // Setup
        int customerId = 99;
        // when deleteCustomer is called return false, simulating failed deletion
        when(mockCustomerDAO.deleteCustomer(customerId)).thenReturn(false);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerHandleException() throws IOException { // deleteCustomer may throw IOException
        // Setup
        int customerId = 99;
        // When deleteCustomer is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).deleteCustomer(customerId);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}