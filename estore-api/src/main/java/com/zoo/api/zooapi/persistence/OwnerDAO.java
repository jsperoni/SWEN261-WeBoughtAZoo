package com.zoo.api.zooapi.persistence;

import java.io.IOException;

import com.zoo.api.zooapi.model.Animal;
import com.zoo.api.zooapi.model.Customer;
import com.zoo.api.zooapi.model.Owner;

public interface OwnerDAO {
    Owner getOwner() throws IOException;
    String getUsername() throws IOException;
    String getPassword() throws IOException;
    Animal addProduct() throws IOException;
    Animal removeProduct() throws IOException;
    Customer getCustomer() throws IOException;
    Customer getCustomerHistory() throws IOException;
    Customer getCustomerInformation() throws IOException;
    Customer getCustomerAddress() throws IOException;
    Customer getCustomerBilling() throws IOException;
}