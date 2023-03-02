package com.zoo.api.zooapi.persistence;

import com.zoo.api.zooapi.model.Customer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class CustomerFileDAO implements CustomerDAO {
    private static final Logger LOG = Logger.getLogger(CustomerFileDAO.class.getName());


    @Override
    public Customer[] getCustomers() throws IOException {
        return new Customer[0];
    }

    @Override
    public Customer[] findCustomers(String containsText) throws IOException {
        return new Customer[0];
    }

    @Override
    public Customer getCustomer(int id) throws IOException {
        return null;
    }

    @Override
    public Customer createCustomer(Customer customer) throws IOException {
        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer) throws IOException {
        return null;
    }

    @Override
    public boolean deleteCustomer(int id) throws IOException {
        return false;
    }
}
