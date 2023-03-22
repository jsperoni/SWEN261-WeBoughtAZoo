package com.zoo.api.zooapi.controller;

import com.zoo.api.zooapi.model.Customer;
import com.zoo.api.zooapi.persistence.CustomerDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private static final Logger Log = Logger.getLogger(CustomerController.class.getName());
    private CustomerDAO customerDao;

    public CustomerController(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping("/by_id/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
         try {
            Customer customer = customerDao.getCustomer(id);
            if (customer != null)
                return new ResponseEntity<Customer>(customer,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Customer[]> getCustomers() {
        Log.info("GET /customers");

        try {
            Customer customer[] = customerDao.getCustomers();
            return new ResponseEntity<Customer[]>(customer,HttpStatus.OK);
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("?name={containsText}")
    public ResponseEntity<Customer> searchCustomer(@PathVariable String containsText) {
        Log.info("GET /customers/?name="+containsText);

        try{
            Customer customer = customerDao.searchCustomer(containsText);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } 
        catch(IOException e){
        Log.log(Level.SEVERE,e.getLocalizedMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }

    @GetMapping("login")
    public ResponseEntity<Customer> login(@RequestParam String username, @RequestParam(required = false, defaultValue = "") String password) {
        Log.info("GET /customers/login?username="+ username +"&password="+ password);

        try {
            Customer customer = customerDao.login(username, password);
            if (customer != null) {
                return new ResponseEntity<>(customer, HttpStatus.OK);
            }
            else {
                Customer newCustomer = new Customer(0, username, null, null, null);
                newCustomer = customerDao.createCustomer(newCustomer);
                return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
            }
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Log.info("POST /customers " + customer);

        // Replace below with your implementation
        try {
            if(customerDao.getCustomer(customer.getId()) == null){
                Customer customernew = customerDao.createCustomer(customer);
                if (customernew != null) {
                    return new ResponseEntity<Customer>(customernew,HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<Customer>(HttpStatus.CONFLICT);
                }
            } else{
                return new ResponseEntity<Customer>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        Log.info("PUT /customers " + customer);

        // Replace below with your implementation
        try {
            Customer customernew = customerDao.updateCustomer(customer);
            if (customernew == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                return new ResponseEntity<>(customernew, HttpStatus.OK);
            }
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/by_id/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable int id) {
        Log.info("DELETE /customers/" + id);

        try {
            if (customerDao.deleteCustomer(id))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
