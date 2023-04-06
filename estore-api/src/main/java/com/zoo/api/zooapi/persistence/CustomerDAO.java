package com.zoo.api.zooapi.persistence;

import com.zoo.api.zooapi.model.Customer;

import java.io.IOException;

public interface CustomerDAO {

    /**
     * Retrieves all {@linkplain Customer customers}
     * @return An array of {@link Customer customer} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Customer[] getCustomers() throws IOException;

    /**
     * Finds all {@linkplain Customer customers} whose name contains the given text
     * @param containsText The text to match against
     * @return An array of {@link Customer customers} whose names contains the given text, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Customer[] findCustomers(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Customer customer} with the given id
     * @param id The id of the {@link Customer customer} to get
     * @return a {@link Customer customer} object with the matching id
     * <br>
     * null if no {@link Customer customer} with a matching id is found
     * @throws IOException if an issue with underlying storage
     */
    Customer getCustomer(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Customer customer}
     * @param username {@linkplain Customer customer} customer's username to search for
     * <br>
     * @return new {@link Customer customer} if a customer with the given username is found, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    Customer searchCustomer(String username) throws IOException;

    /**
     * Creates and saves a {@linkplain Customer customer}
     * @param customer {@linkplain Customer customer} object to be created and saved
     * <br>
     * The id of the customer object is ignored and a new uniqe id is assigned
     * @return new {@link Customer customer} if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    Customer createCustomer(Customer customer) throws IOException;

    /**
     * Updates and saves a {@linkplain Customer customer}
     * @param customer {@linkplain Customer customer} object to be updated and saved
     * <br>
     * The id of the customer object is used to find the customer to update
     * @return updated {@link Customer customer} if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    Customer updateCustomer(Customer customer) throws IOException;

    /**
     * Adds an animal to the customer's product history
     * @param customer {@linkplain Customer customer} object to be updated and saved
     * <br>
     * The id of the customer object is used to find the customer to update
     * @param animalId {@linkplain Customer customer} object to be updated and saved
     * <br>
     * The id of the animal object is used to find the animal to add to the customer's product history
     * @return updated {@link Customer customer} if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    Customer addToProductHistory(Customer customer, int animalId) throws IOException;

    /**
     * Logs in a customer
     * @param username {@linkplain Customer customer} customer's username to search for
     * <br>
     * @param password {@linkplain Customer customer} customer's password to search for
     * <br>
     * @return new {@link Customer customer} if a customer with the given username and password is found, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    Customer login(String username, String password) throws IOException;

    /**
     * Deletes a {@linkplain Customer customer}
     * @param id The id of the {@link Customer customer} to delete
     * @return true if successful, false otherwise
     * @throws IOException if an issue with underlying storage
     */
    boolean deleteCustomer(int id) throws IOException;
}
