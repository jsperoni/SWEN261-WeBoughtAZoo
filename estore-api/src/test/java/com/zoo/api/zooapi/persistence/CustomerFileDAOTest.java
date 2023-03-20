package com.zoo.api.zooapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.api.zooapi.model.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Customer File DAO class
 *
 * @author Group 6F
 */
@Tag("Persistence-tier")
public class CustomerFileDAOTest {
    CustomerFileDAO customerFileDAO;
    Customer[] testCustomers;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException If the file cannot be created
     */
    @BeforeEach
    public void setupCustomerFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testCustomers = new Customer[3];
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        testCustomers[0] = new Customer(9,"goodstudent123", personal, card, history);
        testCustomers[1] = new Customer(39,"gccismajor", personal, card, history);
        testCustomers[2] = new Customer(45,"itouchgrass123", personal, card, history);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the customer array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Customer[].class))
                .thenReturn(testCustomers);
        customerFileDAO = new CustomerFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetCustomers() {
        // Invoke
        Customer[] customers = customerFileDAO.getCustomers();

        // Analyze
        assertEquals(customers.length,testCustomers.length);
        for (int i = 0; i < testCustomers.length;++i)
            assertEquals(customers[i],testCustomers[i]);
    }

    @Test
    public void testFindCustomers() {
        // Invoke
        Customer[] customers = customerFileDAO.findCustomers("123");

        // Analyze
        assertEquals(customers.length,2);
        assertEquals(customers[0],testCustomers[0]);
        assertEquals(customers[1],testCustomers[2]);


    }

    @Test
    public void testGetCustomer() {
        // Invoke
        Customer customer = customerFileDAO.getCustomer(9);

        // Analzye
        assertEquals(customer,testCustomers[0]);
    }

    @Test
    public void testDeleteCustomer() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> customerFileDAO.deleteCustomer(9),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test customers array - 1 (because of the delete)
        // Because customers attribute of CustomerFileDAO is package private
        // we can access it directly
        assertEquals(customerFileDAO.customers.size(),testCustomers.length-1);
    }

    @Test
    public void testCreateCustomer() {
        // Setup
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        Customer customer = new Customer(102,"Wonder-Person", personal, card, history);

        // Invoke
        Customer result = assertDoesNotThrow(() -> customerFileDAO.createCustomer(customer),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Customer actual = customerFileDAO.getCustomer(customer.getId());
        assertEquals(actual.getId(),customer.getId());
        assertEquals(actual.getUsername(),customer.getUsername());
    }

    @Test
    public void testUpdateCustomer() {
        // Setup
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        Customer customer = new Customer(99,"Galactic-Agent", personal, card, history);

        // Invoke
        Customer result = assertDoesNotThrow(() -> customerFileDAO.updateCustomer(customer),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Customer actual = customerFileDAO.getCustomer(customer.getId());
        assertEquals(actual,customer);
    }

    @Test
    public void testSaveException() throws IOException{
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Customer[].class));

                
        Customer customer = new Customer(102,"Wi-Fire", personal, card, history);

        assertThrows(IOException.class,
                        () -> customerFileDAO.createCustomer(customer),
                        "IOException not thrown");
    }

    @Test
    public void testGetCustomerNotFound() {
        // Invoke
        Customer customer = customerFileDAO.getCustomer(98);

        // Analyze
        assertEquals(customer,null);
    }

    @Test
    public void testDeleteCustomerNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> customerFileDAO.deleteCustomer(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(customerFileDAO.customers.size(),testCustomers.length);
    }

    @Test
    public void testUpdateCustomerNotFound() {
        // Setup
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        Customer customer = new Customer(98,"Bolt", personal, card, history);

        // Invoke
        Customer result = assertDoesNotThrow(() -> customerFileDAO.updateCustomer(customer),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the CustomerFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Customer[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new CustomerFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
