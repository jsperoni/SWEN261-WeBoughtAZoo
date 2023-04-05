package com.zoo.api.zooapi.persistence;

import com.zoo.api.zooapi.model.Animal;
import com.zoo.api.zooapi.model.ShoppingCart;
import java.io.IOException;

public interface ShoppingCartDAO {

    /**
     * Retrieves all {@linkplain ShoppingCart shopping carts}
     * @return An array of {@link ShoppingCart shopping cart} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart[] getShoppingCarts() throws IOException;

    /**
     * Retrieves a {@linkplain ShoppingCart shopping cart} with the given id
     * @param id The id of the {@link ShoppingCart shopping cart} to get
     * @return a {@link ShoppingCart shopping cart} object with the matching id
     * <br>
     * null if no {@link ShoppingCart shopping cart} with a matching id is found
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart getShoppingCart(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain ShoppingCart shopping cart}
     * @param shoppingCart {@linkplain ShoppingCart shopping cart} object to be created and saved
     * <br>
     * The id of the shopping cart object is ignored and a new uniqe id is assigned
     * @return new {@link ShoppingCart shopping cart} if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart createShoppingCart(ShoppingCart shoppingCart) throws IOException;

    /**
     * Updates and saves a {@linkplain ShoppingCart shopping cart}
     * @param shoppingCart {@linkplain ShoppingCart shopping cart} object to be updated and saved
     * @return updated {@link ShoppingCart shopping cart} if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) throws IOException;

    /**
     * Adds an animal to a shopping cart
     * @param id The id of the {@link ShoppingCart shopping cart} to add the animal to
     * @param animalId The id of the {@link Animal animal} to add to the shopping cart
     * @return updated {@link ShoppingCart shopping cart} if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart addAnimalToShoppingCart(int id, int animalId) throws IOException;

    /**
     * Removes an animal from a shopping cart
     * @param id The id of the {@link ShoppingCart shopping cart} to remove the animal from
     * @param animalId The id of the {@link Animal animal} to remove from the shopping cart
     * @return updated {@link ShoppingCart shopping cart} if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart removeAnimalFromShoppingCart(int id, int animalId) throws IOException;

    /**
     * Checks out a shopping cart
     * @param id The id of the {@link ShoppingCart shopping cart} to check out
     * @return updated {@link ShoppingCart shopping cart} if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart checkoutShoppingCart(int id) throws IOException;

    /**
     * Deletes a shopping cart
     * @param id The id of the {@link ShoppingCart shopping cart} to delete
     * @return true if successful, false otherwise
     * @throws IOException if an issue with underlying storage
     */
    boolean deleteShoppingCart(int id) throws IOException;

    /**
     * Retrieves all checked out {@linkplain ShoppingCart shopping carts}
     * @return An array of {@link ShoppingCart shopping cart} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    int[] getCheckedOutAnimals() throws IOException;
}
