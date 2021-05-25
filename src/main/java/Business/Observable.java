package Business;

import Presentation.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class
 * Class responsible of informing all the observers about any change, as well as adding or removing any observers
 * */
public class Observable {

    private List<Observer> users = new ArrayList<Observer>();

    /**
     * Method that adds a new observer
     *
     * @param observer - new observer
     * */
    public void addObserver(Observer observer) {
        users.add(observer);
    }

    /**
     * Method that removes an observer
     *
     * @param observer - observer to be removed
     * */
    public void removeObserver(Observer observer) {
        users.remove(observer);
    }

    /**
     * Method that notifies every observer when update happens
     * */
    public void notifyObserver() {

        for (Observer user : users) {
            user.update();
        }
    }
}