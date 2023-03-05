package com.zoo.api.zooapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

public class Customer {
    private static final Logger LOG = Logger.getLogger(Customer.class.getName());

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password; // Security is not a concern for this project
    @JsonProperty("personal") private String[] personal;
    @JsonProperty("card") private String[] card;

    public Customer(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("password") String password,
            @JsonProperty("personal") String[] personal, @JsonProperty("card") String[] card) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.personal = personal;
        this.card = card;
    }

    public int getId() {return id;}

    public void setUsername(String username) {this.username = username;}

    public String getUsername() {return username;}

    public void setPassword(String password) {this.password = password;}

    public String getPassword() {return password;}

    public String[] getPersonal() {return this.personal;}

    public void setPersonal(String[] personal) {this.personal = personal;}

    public String[] getCard() {return this.card;}

    public void setCard(String[] card) {this.card = card;}

    @Override
    public String toString() {
        return String.format("customer [id=%d, username=%s]",id,username);
    }
}
