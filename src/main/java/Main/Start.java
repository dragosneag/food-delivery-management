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

        MainView view = new MainView();
        view.initialize();
    }
}