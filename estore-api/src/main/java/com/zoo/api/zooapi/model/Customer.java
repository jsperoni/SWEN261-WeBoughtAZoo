package com.zoo.api.zooapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

public class Customer {
    private static final Logger LOG = Logger.getLogger(Customer.class.getName());

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password; // Security is not a concern for this project

    public Customer(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {return id;}

    public void setUsername(String username) {this.username = username;}

    public String getUsername() {return username;}

    public void setPassword(String password) {this.password = password;}

    public String getPassword() {return password;}

    @Override
    public String toString() {
        return String.format("customer [id=%d, username=%s]",id,username);
    }
}
