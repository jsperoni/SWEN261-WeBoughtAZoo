package com.zoo.api.zooapi.persistence;

import com.zoo.api.zooapi.model.Customer;

import java.io.IOException;

public interface CustomerDAO {

    Customer[] getCustomers() throws IOException;

    Customer[] findCustomers(String containsText) throws IOException;

    Customer getCustomer(int id) throws IOException;

    Customer searchCustomer(String username) throws IOException;

    Customer createCustomer(Customer customer) throws IOException;

    Customer updateCustomer(Customer customer) throws IOException;

    Customer login(String username, String password) throws IOException;

    boolean deleteCustomer(int id) throws IOException;
}
