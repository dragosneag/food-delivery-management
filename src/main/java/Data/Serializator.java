package Data;

import Business.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Serializator class
 * Generic class responsible with managing the “database”. It transforms the objects into text and stores it into a text file.
 * That file is later accessible again, when the deserialization takes place, the text being converted back into objects.
 * */
public class Serializator<T> {

    private String className;
    private String file;

    public Serializator(String className) {
        this.className = className;
        file = "D:\\Documente\\Facultate\\An 2\\Semestrul 2\\FPT\\Laboratory\\Assignment 4\\" + className + ".txt";
    }

    /**
     * Method that converts objects into text and stores them into a text file
     *
     * @param objects - list of objects to be written in the txt file
     * */
    public void serialize(HashSet<T> objects) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(objects);

            outputStream.close();
            fileOutputStream.close();

            System.out.println("Serialized data is saved in " + file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that converts text into objects and returns them to the application
     * */
    public HashSet<T> deserialize() {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);

            HashSet<T> objects = new HashSet<T>();
            objects = (HashSet<T>) in.readObject();

            in.close();
            fileInputStream.close();

            System.out.println("Objects have been deserialized!");

            return objects;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new HashSet<T>();
    }
}