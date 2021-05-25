package Data;

import Business.BaseProduct;
import Business.CompositeProduct;
import Business.MenuItem;
import Business.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Class that contains two methods, used to display the table
 * for each object table.
 * */
public class TableManager {

    /**
     * Method that uses reflection in order to convert objects into a table
     * @param objectList - list of objects that will be displayed on rows and columns
     * */
    public JTable fillMenuTable(List<MenuItem> objectList) {

        int n, i, j = 0;
        DefaultTableModel tableModel;
        while (objectList.get(j) instanceof CompositeProduct) {
            j++;
        }
        n = objectList.get(j).getClass().getDeclaredFields().length;
        String[] columns = new String[n];
        i = 0;
        for(Field declaredField : objectList.get(j).getClass().getDeclaredFields()){

            declaredField.setAccessible(true);
            columns[i] = declaredField.getName();
            i++;
        }
        tableModel = new DefaultTableModel(columns, 0);
        for(MenuItem object : objectList){
            String[] rows = new String[n];
            rows[0] = object.getTitle();
            rows[1] = String.valueOf(object.computeRating());
            rows[2] = String.valueOf(object.computeCalories());
            rows[3] = String.valueOf(object.computeProtein());
            rows[4] = String.valueOf(object.computeFat());
            rows[5] = String.valueOf(object.computeSodium());
            rows[6] = String.valueOf(object.computePrice());
            tableModel.addRow(rows);
        }
        return new JTable(tableModel);
    }

    /**
     * Method that used to convert order objects into a table
     * @param objectList - list of objects that will be displayed on rows and columns
     * */
    public JTable fillOrdersTable(Map<Order, Collection<MenuItem>> objectList) {
        DefaultTableModel tableModel;
        String[] columns = new String[4];
        columns[0] = "OrderID";
        columns[1] = "Client";
        columns[2] = "Date";
        columns[3] = "Products";
        tableModel = new DefaultTableModel(columns, 0);
        for (Map.Entry<Order, Collection<MenuItem>> orderCollectionEntry : objectList.entrySet()) {
            String[] rows = new String[4];
            rows[0] = String.valueOf(orderCollectionEntry.getKey().getOrderID());
            rows[1] = String.valueOf(orderCollectionEntry.getKey().getClientID());
            rows[2] = orderCollectionEntry.getKey().getOrderDate().toString();
            rows[3] = orderCollectionEntry.getValue().toString();
            tableModel.addRow(rows);
        }
        return new JTable(tableModel);
    }
}
