package Presentation;

/**
 * Employee class
 * This class is responsible with the Observer method update, which announces the employee when an order was placed
 * */
public class Employee implements Observer {

    @Override
    public void update() {
        System.out.println("Order just came in!");
    }
}