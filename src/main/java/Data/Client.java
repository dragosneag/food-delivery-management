package Data;

import Business.MenuItem;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Model class used to represent a client.
 * It contains the following variables:
 * - id - the id of each client
 * - name - name of each client
 * - username - unique username of each client
 * - password - password of each client
 * - phone - phone number of each client
 * - address - used to represent the delivery address of each client
 * */
public class Client implements Serializable {

    private int id;
    private String name;
    private String username;
    private String password;
    private int phone;
    private String address;

    /**
     * Client constructor
     * */
    public Client(int id, String name, String username, String password, int phone, String address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    /**
     * id getter
     * @return id - the known id
     * */
    public int getId() {
        return id;
    }

    /**
     * id setter
     * @param id - the new id
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * name getter
     * @return name - the known name
     * */
    public String getName() {
        return name;
    }

    /**
     * name setter
     * @param name - the new name
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * username getter
     * @return username - the known username
     * */
    public String getUsername() {
        return username;
    }

    /**
     * username setter
     * @param username - the new username
     * */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * password getter
     * @return password - the known password
     * */
    public String getPassword() {
        return password;
    }

    /**
     * password setter
     * @param password - the new password
     * */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * phone getter
     * @return phone - the known phone
     * */
    public int getPhone() {
        return phone;
    }

    /**
     * phone setter
     * @param phone - the new phone
     * */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     * address getter
     * @return address - the known address
     * */
    public String getAddress() {
        return address;
    }

    /**
     * address setter
     * @param address - the new address
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MenuItem) {
            return getUsername().equals(((Client)obj).getUsername());
        }
        return false;
    }
}
