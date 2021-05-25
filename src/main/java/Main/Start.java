package Main;

import Business.BaseProduct;
import Business.DeliveryService;
import Business.MenuItem;
import Business.Order;
import Data.Client;
import Data.FileWriter;
import Data.Serializator;
import Presentation.MainView;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

/**
 * Main class - initializes the user interface
 * */
public class Start {

    public static void main(String[] args) {

        /*HashSet<Client> clients = new HashSet<Client>();
        Client client = new Client(1, "Dragos Neag", "dragosneag", "dragos", 0745650472, "Str.Livezii, nr.40, Cluj-Napoca");
        clients.add(client);
        Serializator<Client> clientSerializator = new Serializator("clients");
        clientSerializator.serialize(clients);
         */

        //System.out.println(java.util.Calendar.getInstance().getTime().getYear());

        MainView view = new MainView();
        view.initialize();

        /*Random random = new Random();
        DeliveryService deliveryService = new DeliveryService();
        for (MenuItem menuItem : deliveryService.getMenuItemList()) {
            BaseProduct baseProduct = new BaseProduct(menuItem.getTitle(), menuItem.computeRating(), menuItem.computeCalories(), menuItem.computeProtein(), menuItem.computeFat(), menuItem.computeSodium(), random.nextInt(100 - 5) + 5);
            deliveryService.editProduct(baseProduct);
        }*/

        /*Serializator serializator = new Serializator();
        serializator.setCSVFile("D:\\Documente\\Facultate\\An 2\\Semestrul 2\\FPT\\Laboratory\\Assignment 4\\epi_r.csv");
        BaseProduct product = (BaseProduct)serializator.deserialize();
        product.toString();*/
    }
}