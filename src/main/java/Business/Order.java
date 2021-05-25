package Business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Model class used to represent an order.
 * It contains the following variables:
 * - orderID - the id of each order
 * - clientID - id of the client that placed the order
 * - orderDate - date when the order was placed
 * - menuItemList - contents of an order
 * */
public class Order implements Serializable {

    private int orderID;
    private int clientID;
    private Date orderDate;
    private List<MenuItem> menuItemList;

    /**
     * Order constructor
     * */
    public Order(int orderID, int clientID, Date orderDate, List<MenuItem> menuItemList) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = orderDate;
        this.menuItemList = menuItemList;
    }

    /**
     * orderID getter
     * @return orderID - the known orderID
     * */
    public int getOrderID() {
        return orderID;
    }

    /**
     * orderID setter
     * @param orderID - the new orderID
     * */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    /**
     * clientID getter
     * @return clientID - the known clientID
     * */
    public int getClientID() {
        return clientID;
    }

    /**
     * clientID setter
     * @param clientID - the new clientID
     * */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * orderDate getter
     * @return orderDate - the known orderDate
     * */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * orderDate setter
     * @param orderDate - the new orderDate
     * */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * menuItemList getter
     * @return menuItemList - the known menuItemList
     * */
    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    /**
     * menuItemList setter
     * @param menuItemList - the new menuItemList
     * */
    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    @Override
    public int hashCode() {
        return orderID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order) {
            if (orderID == ((Order) obj).getOrderID()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", clientID=" + clientID +
                ", orderDate=" + orderDate +
                '}';
    }
}