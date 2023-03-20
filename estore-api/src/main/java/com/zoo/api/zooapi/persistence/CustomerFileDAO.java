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
        try {
            load();  // load the Animals from the file
        } catch (IOException e){
            LOG.severe("Unable to load shopping carts from file: " + filename + " " + e.getMessage());
        }
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private Customer[] getCustomerArray() {
        return getCustomerArray(null);
    }

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

    private boolean save() throws IOException {
        Customer[] Customerarray = getCustomers();
        objectMapper.writeValue(new File(filename), Customerarray);
        return true;
    }

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

    @Override
    public Customer[] getCustomers() {
        synchronized(customers){
            return getCustomerArray();
        }
    }

    @Override
    public Customer[] findCustomers(String containsText) {
        synchronized(customers){
            return getCustomerArray(containsText);
        }
    }

    @Override
    public Customer getCustomer(int id) {
        synchronized(customers) {
            if (customers.containsKey(id)) 
                return customers.get(id);
            else
                return null;
        }
    }

    @Override
    public Customer createCustomer(Customer customer) throws IOException {
        synchronized(customers){
            Customer newCustomer = new Customer(nextId, customer.getUsername(), customer.getPersonal(), customer.getCard(), customer.getHistory());
            customers.put(newCustomer.getId(), newCustomer);
            save();
            return newCustomer;
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) throws IOException {
        synchronized(customers) {
            if (customers.containsKey(customer.getId()) == false)
                return null;

            customers.put(customer.getId(), customer);
            save();
            return customer;
        }
    }

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
}
