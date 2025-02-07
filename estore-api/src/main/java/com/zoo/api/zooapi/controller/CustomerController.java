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

    /**
     * Constructor for CustomerController. Takes in a CustomerDAO object.
     * @param customerDao CustomerDAO object to be used by the controller. Accepts any object implementing the CustomerDAO interface.
     */
    public CustomerController(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * GET /customers/{id} - Returns a customer with the given id.
     * @param id The id of the customer to be returned.
     * @return A ResponseEntity containing the customer with the given id, or a 404 if no customer with the given id exists.
     */
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

    /**
     * GET /customers - Returns all customers.
     * @return A ResponseEntity containing an array of all customers.
     */
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



    /**
     * POST /customers - Creates a new customer.
     * @RequestParam customer The customer to be created.
     * @return A ResponseEntity containing the newly created customer.
     */
    @GetMapping("")
    public ResponseEntity<Customer> searchCustomer(@RequestParam(value="name") String containsText) {
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

    /**
     * POST /customers - Creates a new customer.
     * @RequestParam username The username of the customer to log in as or create.
     * @RequestParam password The password of the customer to log in as or create.
     * @return A ResponseEntity containing the newly created customer.
     */
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

    /**
     * POST /customers - Creates a new customer.
     * @RequestBody customer The customer to be created.
     * @return A ResponseEntity containing the newly created customer.
     */
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

    /**
     * PUT /customers - Updates an existing customer.
     * @RequestBody customer The customer to be updated.
     * @return A ResponseEntity containing the updated customer.
     */
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

    /**
     * DELETE /customers/{id} - Deletes a customer with the given id.
     * @param id The id of the customer to be deleted.
     * @return A ResponseEntity containing the deleted customer.
     */
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

    /**
     * POST /customers/history/{customerId}/{animalId} - Adds an animal to the customer's history.
     * @param customerId The id of the customer to add the animal to.
     * @param animalId The id of the animal to add to the customer's history.
     * @return A ResponseEntity containing the updated customer.
     */
    @PostMapping("/history/{customerId}/{animalId}")
    public ResponseEntity<Customer> addToProductHistory(@PathVariable int customerId, @PathVariable int animalId){
        try {
            Customer customer = customerDao.getCustomer(customerId);
            if (customer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Customer newCustomer = customerDao.addToProductHistory(customer,animalId);
            if (newCustomer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(newCustomer,HttpStatus.OK);
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /customers/history/{customerId} - Gets the customer's history.
     * @param customerId The id of the customer to get the history of.
     * @return A ResponseEntity containing the customer's history.
     */
    @GetMapping("/history/{customerId}")
    public ResponseEntity<int[]> getProductHistory(@PathVariable int customerId){
        try {
            Customer customer = customerDao.getCustomer(customerId);
            if (customer != null){
                return new ResponseEntity<>(customer.getHistory(),HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            Log.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
