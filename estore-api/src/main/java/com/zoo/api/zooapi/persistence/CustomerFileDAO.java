package com.zoo.api.zooapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.api.zooapi.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

@Component
public class CustomerFileDAO implements CustomerDAO {
    private static final Logger LOG = Logger.getLogger(CustomerFileDAO.class.getName());

    Map<Integer, Customer> customers;

    private ObjectMapper objectMapper;
    private String filename;

    private static int nextId;

    public CustomerFileDAO(@Value("data/customers.json") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Returns an array of all customers
     * @return an array of all customers
     */
    private Customer[] getCustomerArray() {
        return getCustomerArray(null);
    }

    /**
     * Returns an array of all customers that contain the given text
     * @param text text to search for
     * @return an array of all customers that contain the given text
     */
    private Customer[] getCustomerArray(String text){
        ArrayList<Customer> CustomerArrayList = new ArrayList<>();

        for (Customer customer : customers.values()){
            if (text == null || customer.getUsername().contains(text)) {
                CustomerArrayList.add(customer);
            }
        }

        Customer[] CustomerArray = new Customer[CustomerArrayList.size()];
        CustomerArrayList.toArray(CustomerArray);
        return CustomerArray;
    }

    /**
     * Saves the current state of the customers to the file
     * @return true if successful, false otherwise
     * @throws IOException if there is an error writing to the file
     */
    private boolean save() throws IOException {
        Customer[] Customerarray = getCustomers();
        objectMapper.writeValue(new File(filename), Customerarray);
        return true;
    }

    /**
     * Loads the customers from the file
     * @return true if successful, false otherwise
     * @throws IOException if there is an error reading from the file
     */
    private boolean load() throws IOException {
        customers = new TreeMap<>();
        nextId = 0;

        Customer[] customerArray = objectMapper.readValue(new File(filename), Customer[].class);

        for (Customer customer : customerArray){
            customers.put(customer.getId(), customer);
            if(customer.getId() > nextId)
                nextId = customer.getId();
        }

        ++nextId;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer[] getCustomers() {
        synchronized(customers){
            return getCustomerArray();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer[] findCustomers(String containsText) {
        synchronized(customers){
            return getCustomerArray(containsText);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer searchCustomer(String containsText) {
        synchronized(customers){
            Customer[] customerArray = getCustomerArray(containsText);
            return customerArray[0];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getCustomer(int id) {
        synchronized(customers) {
            return customers.getOrDefault(id, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer createCustomer(Customer customer) throws IOException {
        synchronized(customers){
            Customer newCustomer = new Customer(customer.getId() != 0 ? customer.getId() : nextId++, customer.getUsername(), customer.getPersonal(), customer.getCard(), customer.getHistory());
            customers.put(newCustomer.getId(), newCustomer);
            save();
            return newCustomer;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer updateCustomer(Customer customer) throws IOException {
        synchronized(customers) {
            if (!customers.containsKey(customer.getId()))
                return null;

            customers.put(customer.getId(), customer);
            save();
            return customer;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer login(String username, String password) throws IOException {
        synchronized(customers) {
            for (Customer customer : customers.values()) {
                if (customer.getUsername().equals(username) && customer.passwordMatch(password)) {
                    return customer;
                }
            }
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteCustomer(int id) throws IOException {
        synchronized(customers) {
            if (customers.containsKey(id)) {
                customers.remove(id);
                return save();
            }
            else 
                return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer addToProductHistory(Customer customer, int animalId){
        Customer tempCustomer = customer;
        tempCustomer.addToHistory(animalId);
        try {
            Customer finalCustomer = updateCustomer(tempCustomer);
            return finalCustomer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
     

    }
}
