package com.zoo.api.zooapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zoo.api.zooapi.model.Animal;

/**
 * Implements the functionality for JSON file-based peristance for Animals
 *
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author SWEN Faculty
 */
@Component
public class AnimalFileDAO implements AnimalDAO {
    private static final Logger LOG = Logger.getLogger(AnimalFileDAO.class.getName());
    Map<Integer, Animal> animals;   // Provides a local cache of the animal objects
                                   // so that we don't need to read from the file
                                   // each time
    private ObjectMapper objectMapper;  // Provides conversion between Animal
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new Animal
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Animal File Data Access Object
     *
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     *
     * @throws IOException when file cannot be accessed or read from
     */
    public AnimalFileDAO(@Value("${animals.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the Animals from the file
    }

    /**
     * Generates the next id for a new {@linkplain Animal Animal}
     *
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Animal Animals} from the tree map
     *
     * @return  The array of {@link Animal Animals}, may be empty
     */
    private Animal[] getAnimalsArray() {
        return getAnimalsArray(null);
    }

    /**
     * Generates an array of {@linkplain Animal Animals} from the tree map for any
     * {@linkplain Animal Animals} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Animal Animals}
     * in the tree map
     *
     * @return  The array of {@link Animal Animals}, may be empty
     */
    private Animal[] getAnimalsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Animal> AnimalArrayList = new ArrayList<>();

        for (Animal Animal : animals.values()) {
            if (containsText == null || Animal.getName().contains(containsText)) {
                AnimalArrayList.add(Animal);
            }
        }

        Animal[] AnimalArray = new Animal[AnimalArrayList.size()];
        AnimalArrayList.toArray(AnimalArray);
        return AnimalArray;
    }

    /**
     * Saves the {@linkplain Animal Animals} from the map into the file as an array of JSON objects
     *
     * @return true if the {@link Animal Animals} were written successfully
     *
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Animal[] AnimalArray = getAnimalsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),AnimalArray);
        return true;
    }

    /**
     * Loads {@linkplain Animal Animals} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     *
     * @return true if the file was read successfully
     *
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        animals = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of Animals
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Animal[] AnimalArray = objectMapper.readValue(new File(filename), Animal[].class);

        // Add each Animal to the tree map and keep track of the greatest id
        for (Animal Animal : AnimalArray) {
            animals.put(Animal.getId(),Animal);
            if (Animal.getId() > nextId)
                nextId = Animal.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Animal[] getAnimals() {
        synchronized(animals) {
            return getAnimalsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Animal[] findAnimals(String containsText) {
        synchronized(animals) {
            return getAnimalsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Animal getAnimal(int id) {
        synchronized(animals) {
            if (animals.containsKey(id))
                return animals.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Animal createAnimal(Animal animal) throws IOException {
        synchronized(animals) {
            // We create a new Animal object because the id field is immutable
            // and we need to assign the next unique id
            Animal newAnimal = new Animal(nextId(), animal.getName());
            animals.put(newAnimal.getId(),newAnimal);
            save(); // may throw an IOException
            return newAnimal;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Animal updateAnimal(Animal animal) throws IOException {
        synchronized(animals) {
            if (animals.containsKey(animal.getId()) == false)
                return null;  // Animal does not exist

            animals.put(animal.getId(), animal);
            save(); // may throw an IOException
            return animal;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteAnimal(int id) throws IOException {
        synchronized(animals) {
            if (animals.containsKey(id)) {
                animals.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
