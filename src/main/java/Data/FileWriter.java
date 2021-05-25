package Data;

import Business.MenuItem;
import Business.Order;

import java.util.HashSet;

/**
 * FileWriter class
 * Class responsible with serializing the products, the clients and the orders every time a change
 * takes place in the deliveryService
 * */
public class FileWriter {

    Serializator<MenuItem> productSerializator;
    Serializator<Client> clientSerializator;
    Serializator<Order> orderSerializator;

    /**
     * Update method - used to write any update into the files of each product, client and order
     *
     * @param menuItemList - list of products
     * @param clients - list of clients
     * @param orders - list of orders
     * */
    public void update(HashSet<MenuItem> menuItemList, HashSet<Client> clients, HashSet<Order> orders){

        productSerializator = new Serializator("products");
        clientSerializator = new Serializator("clients");
        orderSerializator = new Serializator("orders");

        productSerializator.serialize(menuItemList);
        clientSerializator.serialize(clients);
        orderSerializator.serialize(orders);
    }
}