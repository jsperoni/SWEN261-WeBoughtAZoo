package com.zoo.api.zooapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a animal entity
 * 
 * @author SWEN Faculty
 */
public class Animal {
    private static final Logger LOG = Logger.getLogger(Animal.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "animal [id=%d, name=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;

    /**
     * Create a animal with the given id and name
     * @param id The id of the animal
     * @param name The name of the animal
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Animal(@JsonProperty("id") int id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the id of the animal
     * @return The id of the animal
     */
    public int getId() {return id;}

    /**
     * Sets the name of the animal - necessary for JSON object to Java object deserialization
     * @param name The name of the animal
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the animal
     * @return The name of the animal
     */
    public String getName() {return name;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name);
    }
}
