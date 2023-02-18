package com.zoo.api.zooapi.persistence;

import java.io.IOException;
import com.zoo.api.zooapi.model.Animal;

/**
 * Defines the interface for animal object persistence
 * 
 * @author SWEN Faculty
 */
public interface AnimalDAO {
    /**
     * Retrieves all {@linkplain Animal animales}
     * 
     * @return An array of {@link Animal animal} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Animal[] getAnimals() throws IOException;

    /**
     * Finds all {@linkplain Animal animales} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Animal animales} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Animal[] findAnimals(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Animal animal} with the given id
     * 
     * @param id The id of the {@link Animal animal} to get
     * 
     * @return a {@link Animal animal} object with the matching id
     * <br>
     * null if no {@link Animal animal} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Animal getAnimal(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Animal animal}
     * 
     * @param animal {@linkplain Animal animal} object to be created and saved
     * <br>
     * The id of the animal object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Animal animal} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Animal createAnimal(Animal animal) throws IOException;

    /**
     * Updates and saves a {@linkplain Animal animal}
     * 
     * @param {@link Animal animal} object to be updated and saved
     * 
     * @return updated {@link Animal animal} if successful, null if
     * {@link Animal animal} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Animal updateAnimal(Animal animal) throws IOException;

    /**
     * Deletes a {@linkplain Animal animal} with the given id
     * 
     * @param id The id of the {@link Animal animal}
     * 
     * @return true if the {@link Animal animal} was deleted
     * <br>
     * false if animal with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteAnimal(int id) throws IOException;
}
