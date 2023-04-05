package com.zoo.api.zooapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner {
    private static final Logger LOG = Logger.getLogger(Owner.class.getName());

    static final String STRING_FORMAT = "owner [id=%d, name=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;

    //subject to change acc to owner attributes
    public Owner(@JsonProperty("id") int id, @JsonProperty("name") String name){
        this.id = id;
        this.name = name;
    }

    // need getters and setters for every attribute of owner
    public int getId() {return id;}

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    @Override
    public String toString(){
        return String.format(STRING_FORMAT, id, name);
    }
}
