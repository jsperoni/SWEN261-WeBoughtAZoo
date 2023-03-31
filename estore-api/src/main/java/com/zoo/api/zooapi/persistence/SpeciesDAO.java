package com.zoo.api.zooapi.persistence;

import com.zoo.api.zooapi.model.Species;

import java.io.IOException;

/**
 * Defines the interface for species object persistence
 * 
 * @author SWEN Faculty
 */
public interface SpeciesDAO {
    /**
     * Retrieves all {@linkplain Species species}
     * 
     * @return An array of {@link Species species} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Species[] getSpeciess() throws IOException;

    /**
     * Finds all {@linkplain Species specieses} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Species specieses} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Species[] findSpeciess(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Species species} with the given id
     * 
     * @param id The id of the {@link Species species} to get
     * 
     * @return a {@link Species species} object with the matching id
     * <br>
     * null if no {@link Species species} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Species getSpecies(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Species species}
     * 
     * @param species {@linkplain Species species} object to be created and saved
     * <br>
     * The id of the species object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Species species} if successful, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    Species createSpecies(Species species) throws IOException;

    /**
     * Updates and saves a {@linkplain Species species}
     * 
     * @param {@link Species species} object to be updated and saved
     * 
     * @return updated {@link Species species} if successful, null if
     * {@link Species species} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Species updateSpecies(Species species) throws IOException;

    /**
     * Deletes a {@linkplain Species species} with the given id
     * 
     * @param id The id of the {@link Species species}
     * 
     * @return true if the {@link Species species} was deleted
     * <br>
     * false if species with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteSpecies(int id) throws IOException;
}
