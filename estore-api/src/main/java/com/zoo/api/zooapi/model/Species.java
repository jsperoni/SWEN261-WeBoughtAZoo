package com.zoo.api.zooapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Represents a animal entity
 * 
 * @author Group 6F
 */
public class Species {
    private static final Logger LOG = Logger.getLogger(Species.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "animal [name=%s]";

    @JsonProperty("name") private String name;
    @JsonProperty("info") private String[] info;


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
    public Species(@JsonProperty("name") String name, @JsonProperty("info") String[] info )  {
        this.name = name;
        this.info = info;
        
    }

    /**
     * Retrieves the name of the animal
     * @return The name of the animal
     */
    public String getName() {return name;}

    public String[] getInfo() {return info;}

    public void setInfo(String[] info) {this.info = info;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,name);
    }
}