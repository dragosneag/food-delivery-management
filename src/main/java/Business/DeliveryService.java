package Business;

import Data.Client;
import Data.FileWriter;
import Data.Serializator;
import Data.Validator;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DeliveryService class
 * Class that implements the IDeliveryServiceProcessing, and it contains the main operations
 * executed by the administrator and the client
 * */
public class DeliveryService extends Observable implements IDeliveryServiceProcessing {

    Map<Order, Collection<MenuItem>> orderCollectionMap;
    HashSet<MenuItem> menuItemList;
    HashSet<Client> clients;
    HashSet<Order> orders;

    Serializator<Client> clientSerializator;
    Serializator<MenuItem> productSerializator;
    Serializator<Order> orderSerializator;

    Validator validator;
    FileWriter fileWriter;
    Observable observable;

    public DeliveryService() {
        //menuItemList = importProducts1("D:\\Documente\\Facultate\\An 2\\Semestrul 2\\FPT\\Laboratory\\Assignment 4\\epi_r.csv");
        productSerializator = new Serializator("products");
        //productSerializator.serialize(menuItemList);
        menuItemList = importProducts();
        clientSerializator = new Serializator("clients");
        clients = clientSerializator.deserialize();
        orderSerializator = new Serializator("orders");
        orders = orderSerializator.deserialize();
        //displayOrders();

        orderCollectionMap = new HashMap<Order, Collection<MenuItem>>();

        fileWriter = new FileWriter();
        validator = new Validator();
        observable = new Observable();
    }

    /*public HashSet<MenuItem> importProducts1(String CSVName) {
        HashSet<MenuItem> products = new HashSet<MenuItem>();
        Path pathToFile = Paths.get(CSVName);
        try (BufferedReader br = new BufferedReader(new FileReader(CSVName))) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                BaseProduct product = createProduct(attributes);
                products.add(product);
                line = br.readLine();
            }
            br.close();
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BaseProduct createProduct(String[] attributes) {
        int i = 1;
        Random random = new Random();
        String title = attributes[0];
        float rating;
        int calories, proteins, fats, sodium, price;
        String comma = "";
        while(isWord(attributes[i])) {
            title = title + comma + attributes[i];
            comma = ", ";
            i++;
        }
        if(!attributes[i].isEmpty()) {
            rating = Float.parseFloat(attributes[i]);
        } else {
            rating = 0;
        }
        i++;
        calories = instantiateField(attributes[i]);
        i++;
        proteins = instantiateField(attributes[i]);
        i++;
        fats = instantiateField(attributes[i]);
        i++;
        sodium = instantiateField(attributes[i]);
        i++;
        price = random.nextInt(100 - 5) + 5;
        return new BaseProduct(title, rating, calories, proteins, fats, sodium, price);
    }

    private static int instantiateField(String attribute) {

        int field;
        if(!attribute.isEmpty()) {
            field = (int) Float.parseFloat(attribute);
        } else {
            field = 0;
        }
        return field;
    }

    private static boolean isWord(String string) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }*/

    public HashSet<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public HashSet<Client> getClients() {
        return clients;
    }

    public HashSet<Order> getOrders() {
        return orders;
    }

    public Map<Order, Collection<MenuItem>> getOrderCollectionMap() {
        return orderCollectionMap;
    }

    @Override
    public HashSet<MenuItem> importProducts() {

        assert wellFormed();
        return productSerializator.deserialize();
    }

    @Override
    public void editProduct(MenuItem product) {

        assert wellFormed();
        assert product != null;
        try {
            validator.validateProduct((BaseProduct) product);
            for (MenuItem menuItem : menuItemList) {
                if (menuItem.getTitle().equals(product.getTitle())) {
                    menuItemList.remove(menuItem);
                    menuItemList.add(product);
                    break;
                }
            }
            assert menuItemList.contains(product);
            fileWriter.update(menuItemList, clients, orders);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void deleteProduct(MenuItem product) {

        assert wellFormed();
        assert product != null;
        for (MenuItem menuItem : menuItemList) {
            if (menuItem.getTitle().equals(product.getTitle())) {
                menuItemList.remove(menuItem);
                break;
            }
        }
        assert !menuItemList.contains(product);
        fileWriter.update(menuItemList, clients, orders);
    }

    @Override
    public void addProduct(MenuItem product) {

        assert wellFormed();
        assert product != null;
        if(product instanceof CompositeProduct) {
            menuItemList.add(product);
            fileWriter.update(menuItemList, clients, orders);
        } else {
            try {
                validator.validateProduct((BaseProduct)product);

                menuItemList.add(product);

                assert menuItemList.contains(product);
                fileWriter.update(menuItemList, clients, orders);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void displayProducts() {

        int count = 0;
        for (MenuItem menuItem : menuItemList) {
            System.out.println(menuItem.toString());
            count++;
        }
        System.out.println(count);
    }

    public void displayOrders() {

        int count = 0;
        for (Order order : orders) {
            System.out.println(order.toString());
            for (MenuItem menuItem : order.getMenuItemList()) {
                System.out.println(menuItem.toString());
            }
            count++;
        }
        System.out.println(count);
    }

    public boolean addClient(Client client) {
        assert wellFormed();
        assert client != null;
        try {
            validator.validateClient(client);
            client.setId(clients.size() + 1);
            clients.add(client);

            assert clients.contains(client);
            fileWriter.update(menuItemList, clients, orders);
            return true;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }

    public void createOrder(int clientId, List<MenuItem> menuItems) {
        assert wellFormed();
        assert !menuItems.isEmpty();
        Order order = new Order(orders.size() + 1, clientId, java.util.Calendar.getInstance().getTime(), menuItems);
        orders.add(order);
        orderCollectionMap.put(order, menuItems);
        generateBill(order);
        observable.notifyObserver();

        assert orders.contains(order);
        fileWriter.update(menuItemList, clients, orders);
    }

    public void doOrder(Order order, Collection<MenuItem> menuItems) {

        orderCollectionMap.remove(order, menuItems);
    }

    public void generateReport(String number, List<Object> objectList) {
        assert wellFormed();
        String file = "D:\\Documente\\Facultate\\An 2\\Semestrul 2\\FPT\\Laboratory\\Assignment 4\\" + "report" + number + ".txt";

        try {
            java.io.FileWriter fileWriter1;
            fileWriter1 = new java.io.FileWriter(file);

            objectList.forEach(o -> {
                try {
                    if(o instanceof Client) {
                        fileWriter1.write(((Client) o).getName() + " " + ((Client) o).getPhone() + " " + ((Client) o).getAddress() + "\n");
                    } else {
                        fileWriter1.write(o.toString() + "\n");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });

            fileWriter1.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateMapReport(String number, Integer[] timesOrdered, List<Object> objectList) {
        assert wellFormed();
        String file = "D:\\Documente\\Facultate\\An 2\\Semestrul 2\\FPT\\Laboratory\\Assignment 4\\" + "report" + number + ".txt";

        try {
            java.io.FileWriter fileWriter1;
            fileWriter1 = new java.io.FileWriter(file);

            final int[] i = {0};
            objectList.forEach(o -> {
                try {
                    if(o instanceof Client) {
                        fileWriter1.write(((Client) o).getName() + " " + ((Client) o).getPhone() + " " + ((Client) o).getAddress() + "\n");
                    } else {
                        fileWriter1.write(o.toString() + " ordered " + timesOrdered[i[0]] + " times\n");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                i[0]++;
            });

            fileWriter1.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateBill(Order order) {
        assert wellFormed();
        String file = "D:\\Documente\\Facultate\\An 2\\Semestrul 2\\FPT\\Laboratory\\Assignment 4\\bill.txt";
        try {
            java.io.FileWriter fileWriter1;
            fileWriter1 = new java.io.FileWriter(file);
            try {
                try {
                    Client client = findClient(order.getClientID());
                    fileWriter1.write(client.getName() + " " + client.getPhone() + " " + client.getAddress() + "\n");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                fileWriter1.write(order.toString() + "\n");
                int price = 0;
                for (MenuItem menuItem : order.getMenuItemList()) {
                    try {
                        fileWriter1.write(menuItem.toString() + "\n");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    price = price + menuItem.computePrice();
                }
                fileWriter1.write("Total: " + price + "\n");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            fileWriter1.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Client findClient(int id) {
        for (Client client : clients) {
            if(client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    protected boolean wellFormed() {
        if (productSerializator == null) {
            return false;
        }
        if (menuItemList == null) {
            return false;
        }
        if (clientSerializator == null) {
            return false;
        }
        if (clients == null) {
            return false;
        }
        if (orderSerializator == null) {
            return false;
        }
        if (orders == null) {
            return false;
        }
        if (fileWriter == null) {
            return false;
        }
        if (validator == null) {
            return false;
        }
        if (observable == null) {
            return false;
        }
        return true;
    }
}