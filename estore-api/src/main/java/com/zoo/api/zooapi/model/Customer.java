package com.zoo.api.zooapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;
/**
 * Represents a Customer entity
 * 
 * @author Group 6F
 */
public class Customer {
    private static final Logger LOG = Logger.getLogger(Customer.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "customer [id=%d, username=%s, password=%s, personal=%s, card=%s, history=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("personal") private String[] personal;    // {address, zip, phone #}
    @JsonProperty("card") private String[] card;            // {name, card #, card exp, cvv, billing zip}
    @JsonProperty("history") private String[] history;      // {purchaseID1, purchaseID2...}

    /**
     * Create a customer with the following information
     * @param id id of the customer
     * @param username customer's username
     * @param personal customer's personal information: address
     * @param card Customer's card information
     * @param history Customer's search history
     */
    public Customer(@JsonProperty("id") int id, @JsonProperty("username") String username,@JsonProperty("personal") String[] personal,
                    @JsonProperty("card") String[] card, @JsonProperty("history") String[] history) {
        this.id = id;
        this.username = username;
        this.personal = personal;
        this.card = card;
        this.history = history;
    }

    /**
     * Retrieves the id of the customer
     * @return The id of the customer
     */
    public int getId() {return id;}

    /**
     * Sets the username of the customer
     * @param username customer's username
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Gets the customer's username
     * @return customer's username
     */
    public String getUsername() {return username;}

    /**
     * Gets the customer's personal information ie address
     * @return the customer's personal information
     */
    public String[] getPersonal() {return this.personal;}

    /**
     * Sets the customer's personal information
     * @param personal Customer's personal information
     */
    public void setPersonal(String[] personal) {this.personal = personal;}

    /**
     * Gets the customer's card information
     * @return Customer's card information
     */
    public String[] getCard() {return this.card;}

    /**
     * Sets the customer's card information
     * @param card customer's card information
     */
    public void setCard(String[] card) {this.card = card;}

    /**
     * Gets the customer's search history
     * @return Customer's search history
     */
    public String[] getHistory() {return this.history;}

    /**
     * Sets the customer's search history
     * @param history Customer's search history
     */
    public void setHistory(String[] history) {this.history = history;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("customer [id=%d, username=%s]",id,username);
    }
}
