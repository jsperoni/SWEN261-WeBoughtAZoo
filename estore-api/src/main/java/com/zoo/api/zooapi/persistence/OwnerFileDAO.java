package com.zoo.api.zooapi.persistence;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.zoo.api.zooapi.model.Animal;
import com.zoo.api.zooapi.model.Customer;
import com.zoo.api.zooapi.model.Owner;

@Component
public class OwnerFileDAO implements OwnerDAO{

    @Override
    public Owner getOwner() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOwner'");
    }

    @Override
    public String getUsername() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

    @Override
    public String getPassword() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }

    @Override
    public Animal addProduct() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
    }

    @Override
    public Animal removeProduct() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeProduct'");
    }

    @Override
    public Customer getCustomer() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomer'");
    }

    @Override
    public Customer getCustomerHistory() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomerHistory'");
    }

    @Override
    public Customer getCustomerInformation() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomerInformation'");
    }

    @Override
    public Customer getCustomerAddress() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomerAddress'");
    }

    @Override
    public Customer getCustomerBilling() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomerBilling'");
    }
    
}
