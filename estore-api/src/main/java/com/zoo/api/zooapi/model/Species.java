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
     * @param name The name of the species
     * @param info The info of the species
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
     * Retrieves the name of the species
     * @return The name of the species
     */
    public String getName() {return name;}

    /**
     * Retrieves the info of the species
     * @return The info of the species
     */
    public String[] getInfo() {return info;}

    /**
     * Sets the info of the species - to be used in JSON object to Java object deserialization
     * @param info The info of the species
     */
    public void setInfo(String[] info) {this.info = info;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,name);
    }
}
