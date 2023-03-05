package com.zoo.api.zooapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoo.api.zooapi.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
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
        load();  // load the Animals from the file
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private boolean save() throws IOException {
        return false;
    }

    private void load() throws IOException {
    }

    @Override
    public Customer[] getCustomers() {
        return new Customer[0];
    }

    @Override
    public Customer[] findCustomers(String containsText) {
        return new Customer[0];
    }

    @Override
    public Customer getCustomer(int id) {
        return null;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public boolean deleteCustomer(int id) {
        return false;
    }
}
