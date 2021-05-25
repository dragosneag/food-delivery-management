package Business;

import Data.Client;

import java.util.HashSet;
import java.util.List;

/**
 * @invariant wellFormed()
 * */
public interface IDeliveryServiceProcessing {

    /**
     * Import products method
     * - populates the products with products from file
     *
     * @pre true
     * @post true
     *
     * */
    HashSet<MenuItem> importProducts();

    /**
     * Edit products method
     *
     * @pre product != null
     * @post menuItemList.contains(product)
     *
     * @param product - product to be edited by the admin
     * */
    void editProduct(MenuItem product);

    /**
     * Delete products method
     *
     * @pre product != null
     * @post !menuItemList.contains(product)
     *
     * @param product - product to be deleted by the admin
     * */
    void deleteProduct(MenuItem product);

    /**
     * Add products method
     *
     * @pre client != null
     * @post menuItemList.contains(product)
     *
     * @param product - product to be added by the admin
     * */
    void addProduct(MenuItem product);

    /**
     * Add client method
     *
     * @pre product != null
     * @post clients.contains(client)
     *
     * @param client - client to be added by the user
     * */
    boolean addClient(Client client);

    /**
     * Create order method
     *
     * @pre !menuItems.isEmpty()
     * @post orders.contains(order)
     *
     * @param clientId - id of the client that placed the order, used for Map key
     * @param menuItems - items from the order
     * */
    void createOrder(int clientId, List<MenuItem> menuItems);

    /**
     * Generate report method
     *
     * @pre true
     * @post true
     *
     * @param number - number of the report file
     * @param objectList - objects that appear in the report
     * */
    void generateReport(String number, List<Object> objectList);

    /**
     * Generate report method as map
     *
     * @pre true
     * @post true
     *
     * @param number - number of the report file
     * @param timesOrdered - number of time an object has been ordered
     * @param objectList - objects that appear in the report
     * */
    void generateMapReport(String number, Integer[] timesOrdered, List<Object> objectList);

    /**
     * Generate bill method
     *
     * @pre true
     * @post true
     *
     * @param order - the order of which details go on the bill
     * */
    void generateBill(Order order);
}