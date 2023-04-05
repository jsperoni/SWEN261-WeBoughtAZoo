package com.zoo.api.zooapi.persistence;

import com.zoo.api.zooapi.model.ShoppingCart;
import java.io.IOException;

public interface ShoppingCartDAO {
    ShoppingCart[] getShoppingCarts() throws IOException;

    ShoppingCart getShoppingCart(int id) throws IOException;

    ShoppingCart createShoppingCart(ShoppingCart shoppingCart) throws IOException;

    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) throws IOException;

    ShoppingCart addAnimalToShoppingCart(int id, int animalId) throws IOException;

    ShoppingCart removeAnimalFromShoppingCart(int id, int animalId) throws IOException;

    ShoppingCart checkoutShoppingCart(int id) throws IOException;

    boolean deleteShoppingCart(int id) throws IOException;

    int[] getCheckedOutAnimals() throws IOException;
}
