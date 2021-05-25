package Presentation;

import Business.*;
import Business.MenuItem;
import Business.Observable;
import Data.Client;
import Data.TableManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.color.ProfileDataException;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Interface class
 * This class manages the tools that generate the graphical user interface
 * */
public class MainView extends JFrame{

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createUserButton;
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JButton createUser2Button;
    private JButton cancelButton;
    private JButton logOutButton;
    private JScrollPane scrollPane2;
    private JTable dbTable;
    private JButton createMenuButton;
    private JButton editProductButton;
    private JTextField productNameField;
    private JButton addButton;
    private JButton createButton;
    private JButton deleteProductButton;
    private JButton addProductButton;
    private JTextField searchProductField;
    private JButton searchProductButton;
    private JButton orderButton;
    private JButton markAsDoneButton;

    private JTextField ratingField;
    private JTextField caloriesField;
    private JTextField proteinsField;
    private JTextField fatsField;
    private JTextField sodiumField;
    private JTextField priceField;
    private JButton editButton;

    //Reports

    private JTextField hourNumber1;
    private JTextField hourNumber2;
    private JButton generateReport1;
    private JTextField productsMoreThan;
    private JButton generateReport2;
    private JTextField clientsMoreThan;
    private JTextField priceMoreThan;
    private JButton generateReport3;
    private JComboBox weekdayBox;
    private JButton generateReport4;

    private DeliveryService deliveryService;
    private TableManager menuItemTableManager;

    private int currentClient;
    private Observable observable = null;
    private Employee employee;

    /**
     * Method for frame initialization
     * */
    public void initialize(){

        this.setTitle("Restaurant");
        this.setSize(700, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        deliveryService = new DeliveryService();
        menuItemTableManager = new TableManager();

        employee = new Employee();

        observable = new Observable();
        observable.addObserver(employee);

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);

        initializeLogin(panel1);
        initializeLoginListeners();

        this.setContentPane(panel1);
        this.setVisible(true);
    }

    /**
     * Method that adds the necessary elements to the login panel
     * @param panel - the login panel
     * */
    private void initializeLogin(JPanel panel) {

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(270, 110, 200, 30);

        Font labelFont = loginLabel.getFont();
        String labelText = loginLabel.getText();

        int stringWidth = loginLabel.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = loginLabel.getWidth();

        double widthRatio = (double)componentWidth / (double)stringWidth;

        int newFontSize = (int)(labelFont.getSize() * widthRatio);
        int componentHeight = loginLabel.getHeight() - 5;

        int fontSizeToUse = Math.min(newFontSize, componentHeight);
        loginLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));

        JLabel userLabel = new JLabel("Username ");
        userLabel.setBounds(170, 200, 200, 30);

        usernameField = new JTextField();
        usernameField.setBounds(240, 200, 200, 30);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(170, 240, 200, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(240, 240, 200, 30);

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(170, 310, 100, 30);

        createUserButton = new JButton("CREATE NEW USER");
        createUserButton.setBounds(290, 310, 150, 30);

        panel.add(loginLabel);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(createUserButton);
    }

    /**
     * Method that contains listeners for the login panel's buttons
     * */
    private void initializeLoginListeners(){

        loginButton.addActionListener(e -> {

            if(usernameField.getText().equals("admin")) {
                if (passwordField.getText().equals("admin")) {
                    JPanel panel2 = new JPanel();
                    panel2.setLayout(null);

                    List<MenuItem> menuItems = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
                    initializeAdmin(panel2, menuItems);
                    initializeAdminListeners();

                    this.setContentPane(panel2);
                    this.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong admin passsword");
                }
            } else {
                if(usernameField.getText().equals("employee")) {
                    if (passwordField.getText().equals("employee")) {
                        JPanel panel2 = new JPanel();
                        panel2.setLayout(null);

                        initializeEmployee(panel2);
                        initializeEmployeeListeners();

                        this.setContentPane(panel2);
                        this.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong employee passsword");
                    }
                } else {
                    HashSet<Client> clients = deliveryService.getClients();
                    int ok = 0;
                    for (Client client : clients) {
                        if (client.getUsername().equals(usernameField.getText())) {
                            ok = 1;
                            if (client.getPassword().equals(passwordField.getText())) {
                                JPanel panel2 = new JPanel();
                                panel2.setLayout(null);

                                currentClient = client.getId();

                                List<MenuItem> menuItems = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
                                initializeClient(panel2, menuItems);
                                initializeClientListeners();

                                this.setContentPane(panel2);
                                this.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Wrong passsword");
                            }
                            break;
                        }
                    }
                    if (ok == 0) {
                        JOptionPane.showMessageDialog(null, "Client not found!");
                    }
                }
            }
        });

        createUserButton.addActionListener(e -> {

            JPanel panel21 = new JPanel();
            panel21.setLayout(null);

            initializeCreateUser(panel21);
            initializeCreateUserListeners();

            this.setContentPane(panel21);
            this.setVisible(true);
        });
    }

    /**
     * Method that adds the necessary elements to the create user panel
     * @param panel - the create user panel
     * */
    private void initializeCreateUser(JPanel panel) {

        JLabel titleLabel = new JLabel("Create User");
        titleLabel.setBounds(270, 65, 200, 30);

        JLabel userLabel = new JLabel("Username ");
        userLabel.setBounds(140, 200, 200, 30);

        usernameField = new JTextField();
        usernameField.setBounds(250, 200, 200, 30);

        JLabel passwordLabel = new JLabel("Password ");
        passwordLabel.setBounds(140, 240, 200, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(250, 240, 200, 30);

        JLabel nameLabel = new JLabel("Name ");
        nameLabel.setBounds(140, 280, 200, 30);

        nameField = new JTextField();
        nameField.setBounds(250, 280, 200, 30);

        JLabel phoneNumberLabel = new JLabel("Phone number ");
        phoneNumberLabel.setBounds(140, 320, 200, 30);

        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(250, 320, 200, 30);

        JLabel addressLabel = new JLabel("Address ");
        addressLabel.setBounds(140, 360, 200, 30);

        addressField = new JTextField();
        addressField.setBounds(250, 360, 200, 30);

        createUser2Button = new JButton("CREATE");
        createUser2Button.setBounds(140, 460, 100, 30);

        cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(300, 460, 150, 30);

        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(phoneNumberLabel);
        panel.add(phoneNumberField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(createUser2Button);
        panel.add(cancelButton);
    }

    /**
     * Method that contains listeners for the user create panel's buttons
     * */
    private void initializeCreateUserListeners() {

        cancelButton.addActionListener(e -> {
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            initializeLogin(panel1);
            initializeLoginListeners();

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        createUser2Button.addActionListener(e -> {

            Client client = new Client(0, nameField.getText(), usernameField.getText(), passwordField.getText(), Integer.parseInt(phoneNumberField.getText()), addressField.getText());
            if(deliveryService.addClient(client)) {
                JPanel panel1 = new JPanel();
                panel1.setLayout(null);

                initializeLogin(panel1);
                initializeLoginListeners();

                this.setContentPane(panel1);
                this.setVisible(true);
            }
        });
    }

    /**
     * Method that adds the necessary elements to the admin panel
     * @param panel - the admin panel
     * */
    private void initializeAdmin(JPanel panel, List<MenuItem> menuItems) {

        JLabel titleLabel = new JLabel("Administrator page");
        titleLabel.setBounds(270, 35, 200, 30);

        logOutButton = new JButton("LOG OUT");
        logOutButton.setBounds(30, 35, 100, 30);

        Collections.sort(menuItems, Comparator.comparing(MenuItem::getTitle));
        dbTable = menuItemTableManager.fillMenuTable(menuItems);
        dbTable.setVisible(true);
        dbTable.setEnabled(true);

        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(30, 100, 450, 250);
        scrollPane2.setViewportView(dbTable);

        createMenuButton = new JButton("NEW MENU");
        createMenuButton.setBounds(30, 360, 100, 30);

        editProductButton = new JButton("EDIT");
        editProductButton.setBounds(140, 360, 100, 30);

        deleteProductButton = new JButton("DELETE");
        deleteProductButton.setBounds(250, 360, 100, 30);

        addProductButton = new JButton("ADD");
        addProductButton.setBounds(360, 360, 100, 30);

        searchProductField = new JTextField();
        searchProductField.setBounds(440, 35, 100, 30);

        searchProductButton = new JButton("SEARCH");
        searchProductButton.setBounds(550, 35, 100, 30);

        //Reports

        //Report 1

        JLabel hour1 = new JLabel("Orders between hour: ");
        hour1.setBounds(30, 440, 150, 30);

        hourNumber1 = new JTextField();
        hourNumber1.setBounds(190, 440, 60, 30);

        JLabel hour2 = new JLabel("and: ");
        hour2.setBounds(260, 440, 50, 30);

        hourNumber2 = new JTextField();
        hourNumber2.setBounds(320, 440, 60, 30);

        generateReport1 = new JButton("GENERATE");
        generateReport1.setBounds(400, 440, 100, 30);

        //Report 2

        JLabel productRange = new JLabel("Products ordered more than: ");
        productRange.setBounds(30, 480, 200, 30);

        productsMoreThan = new JTextField();
        productsMoreThan.setBounds(240, 480, 60, 30);

        generateReport2 = new JButton("GENERATE");
        generateReport2.setBounds(400, 480, 100, 30);

        //Report 3

        JLabel loyalClients1 = new JLabel("Clients who ordered more than: ");
        loyalClients1.setBounds(30, 520, 200, 30);

        clientsMoreThan = new JTextField();
        clientsMoreThan.setBounds(240, 520, 60, 30);

        JLabel loyalClients2 = new JLabel("times,");
        loyalClients2.setBounds(310, 520, 210, 30);

        JLabel loyalClients3 = new JLabel("and order value more than: ");
        loyalClients3.setBounds(30, 560, 200, 30);

        priceMoreThan = new JTextField();
        priceMoreThan.setBounds(230, 560, 60, 30);

        generateReport3 = new JButton("GENERATE");
        generateReport3.setBounds(400, 560, 100, 30);

        //Report 4

        String[] weekDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        JLabel ordersOnDay = new JLabel("Products ordered on: ");
        ordersOnDay.setBounds(30, 600, 150, 30);

        weekdayBox = new JComboBox(weekDays);
        weekdayBox.setBounds(190, 600, 100, 30);

        generateReport4 = new JButton("GENERATE");
        generateReport4.setBounds(400, 600, 100, 30);

        panel.add(titleLabel);
        panel.add(logOutButton);
        panel.add(scrollPane2);
        panel.add(createMenuButton);
        panel.add(editProductButton);
        panel.add(deleteProductButton);
        panel.add(addProductButton);
        panel.add(searchProductField);
        panel.add(searchProductButton);
        panel.add(hour1);
        panel.add(hourNumber1);
        panel.add(hour2);
        panel.add(hourNumber2);
        panel.add(generateReport1);
        panel.add(productRange);
        panel.add(productsMoreThan);
        panel.add(generateReport2);
        panel.add(loyalClients1);
        panel.add(clientsMoreThan);
        panel.add(loyalClients2);
        panel.add(loyalClients3);
        panel.add(priceMoreThan);
        panel.add(generateReport3);
        panel.add(ordersOnDay);
        panel.add(weekdayBox);
        panel.add(generateReport4);
    }

    /**
     * Method that contains listeners for the admin panel's buttons
     * */
    private void initializeAdminListeners() {

        logOutButton.addActionListener(e -> {
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            initializeLogin(panel1);
            initializeLoginListeners();

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        createMenuButton.addActionListener(e -> {

            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            List<MenuItem> menuItems1 = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
            initializeAdmin(panel1, menuItems1);
            initializeAdminListeners();

            JLabel nameLabel = new JLabel("Title ");
            nameLabel.setBounds(30, 400, 100, 30);

            productNameField = new JTextField();
            productNameField.setBounds(140, 400, 150, 30);

            addButton = new JButton("ADD");
            addButton.setBounds(500, 100, 60, 30);

            createButton = new JButton("CREATE");
            createButton.setBounds(300, 400, 100, 30);

            List<MenuItem> menuItems = new ArrayList<MenuItem>();

            dbTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event) {
                    HashSet<MenuItem> menuItems1 = deliveryService.getMenuItemList();
                    for (MenuItem menuItem : menuItems1) {
                        if(menuItem.getTitle().equals(dbTable.getValueAt(dbTable.getSelectedRow(), 0).toString())) {
                            addButton.addActionListener(e1 -> {
                                menuItems.add(menuItem);
                            });
                        }
                    }
                }
            });

            createButton.addActionListener(e1 -> {
                CompositeProduct compositeProduct = new CompositeProduct(productNameField.getText(), menuItems);
                deliveryService.addProduct(compositeProduct);

                JPanel panel2 = new JPanel();
                panel2.setLayout(null);

                List<MenuItem> menuItems2 = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
                initializeAdmin(panel2, menuItems2);
                initializeAdminListeners();

                this.setContentPane(panel2);
                this.setVisible(true);
            });

            panel1.add(nameLabel);
            panel1.add(productNameField);
            panel1.add(addButton);
            panel1.add(createButton);

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        editProductButton.addActionListener(e -> {

            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            List<MenuItem> menuItems1 = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
            initializeAdmin(panel1, menuItems1);
            initializeAdminListeners();

            createButton = new JButton("DISPLAY");
            createButton.setBounds(500, 100, 100, 30);

            List<MenuItem> menuItems = new ArrayList<MenuItem>();

            dbTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    HashSet<MenuItem> menuItems1 = deliveryService.getMenuItemList();
                    for (MenuItem menuItem : menuItems1) {
                        if (menuItem.getTitle().equals(dbTable.getValueAt(dbTable.getSelectedRow(), 0).toString())) {
                            menuItems.add(menuItem);
                        }
                    }
                }
            });


            createButton.addActionListener(e1 -> {
                if(menuItems.size() > 0) {
                    MenuItem item = menuItems.get(menuItems.size() - 1);

                    JPanel panel2 = new JPanel();
                    panel2.setLayout(null);

                    productNameField = new JTextField(item.getTitle());
                    productNameField.setBounds(30, 400, 100, 30);

                    ratingField = new JTextField(String.valueOf(item.computeRating()));
                    ratingField.setBounds(140, 400, 60, 30);

                    caloriesField = new JTextField(String.valueOf(item.computeCalories()));
                    caloriesField.setBounds(210, 400, 60, 30);

                    proteinsField = new JTextField(String.valueOf(item.computeProtein()));
                    proteinsField.setBounds(280, 400, 60, 30);

                    fatsField = new JTextField(String.valueOf(item.computeFat()));
                    fatsField.setBounds(350, 400, 60, 30);

                    sodiumField = new JTextField(String.valueOf(item.computeSodium()));
                    sodiumField.setBounds(410, 400, 60, 30);

                    priceField = new JTextField(String.valueOf(item.computePrice()));
                    priceField.setBounds(480, 400, 60, 30);

                    editButton = new JButton("EDIT");
                    editButton.setBounds(550, 400, 100, 30);

                    editButton.addActionListener(e2 -> {
                        BaseProduct product = new BaseProduct(productNameField.getText(), Float.parseFloat(ratingField.getText()), Integer.parseInt(caloriesField.getText()), Integer.parseInt(proteinsField.getText()), Integer.parseInt(fatsField.getText()), Integer.parseInt(sodiumField.getText()), Integer.parseInt(priceField.getText()));
                        deliveryService.editProduct(product);

                        JPanel panel3 = new JPanel();
                        panel3.setLayout(null);

                        List<MenuItem> menuItemsTable = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
                        initializeAdmin(panel3, menuItemsTable);
                        initializeAdminListeners();

                        this.setContentPane(panel3);
                        this.setVisible(true);
                    });

                    panel2.add(productNameField);
                    panel2.add(ratingField);
                    panel2.add(caloriesField);
                    panel2.add(proteinsField);
                    panel2.add(fatsField);
                    panel2.add(sodiumField);
                    panel2.add(priceField);
                    panel2.add(editButton);

                    List<MenuItem> menuItemsTable = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
                    initializeAdmin(panel2, menuItemsTable);
                    initializeAdminListeners();

                    this.setContentPane(panel2);
                    this.setVisible(true);
                }
            });

            panel1.add(createButton);

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        deleteProductButton.addActionListener(e -> {
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            List<MenuItem> menuItemsTable = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
            initializeAdmin(panel1, menuItemsTable);
            initializeAdminListeners();

            createButton = new JButton("DELETE");
            createButton.setBounds(500, 100, 100, 30);

            List<MenuItem> menuItems = new ArrayList<MenuItem>();

            dbTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    HashSet<MenuItem> menuItems1 = deliveryService.getMenuItemList();
                    for (MenuItem menuItem : menuItems1) {
                        if (menuItem.getTitle().equals(dbTable.getValueAt(dbTable.getSelectedRow(), 0).toString())) {
                            menuItems.add(menuItem);
                            System.out.println(menuItem.toString());
                        }
                    }
                }
            });

            createButton.addActionListener(e1 -> {
                if (menuItems.size() > 0) {
                    MenuItem product = menuItems.get(menuItems.size() - 1);
                    System.out.println(product.toString());
                    deliveryService.deleteProduct(product);

                    JPanel panel3 = new JPanel();
                    panel3.setLayout(null);

                    initializeAdmin(panel3, menuItemsTable);
                    initializeAdminListeners();

                    this.setContentPane(panel3);
                    this.setVisible(true);
                }
            });

            panel1.add(createButton);

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        addProductButton.addActionListener(e -> {
            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            productNameField = new JTextField();
            productNameField.setBounds(30, 400, 100, 30);

            ratingField = new JTextField();
            ratingField.setBounds(140, 400, 60, 30);

            caloriesField = new JTextField();
            caloriesField.setBounds(210, 400, 60, 30);

            proteinsField = new JTextField();
            proteinsField.setBounds(280, 400, 60, 30);

            fatsField = new JTextField();
            fatsField.setBounds(350, 400, 60, 30);

            sodiumField = new JTextField();
            sodiumField.setBounds(410, 400, 60, 30);

            priceField = new JTextField();
            priceField.setBounds(480, 400, 60, 30);

            editButton = new JButton("ADD");
            editButton.setBounds(550, 400, 100, 30);

            editButton.addActionListener(e2 -> {
                BaseProduct product = new BaseProduct(productNameField.getText(), Float.parseFloat(ratingField.getText()), Integer.parseInt(caloriesField.getText()), Integer.parseInt(proteinsField.getText()), Integer.parseInt(fatsField.getText()), Integer.parseInt(sodiumField.getText()), Integer.parseInt(priceField.getText()));
                deliveryService.addProduct(product);

                JPanel panel3 = new JPanel();
                panel3.setLayout(null);

                List<MenuItem> menuItemsTable = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
                initializeAdmin(panel3, menuItemsTable);
                initializeAdminListeners();

                this.setContentPane(panel3);
                this.setVisible(true);
            });

            panel2.add(productNameField);
            panel2.add(ratingField);
            panel2.add(caloriesField);
            panel2.add(proteinsField);
            panel2.add(fatsField);
            panel2.add(sodiumField);
            panel2.add(priceField);
            panel2.add(editButton);

            List<MenuItem> menuItemsTable = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
            initializeAdmin(panel2, menuItemsTable);
            initializeAdminListeners();

            this.setContentPane(panel2);
            this.setVisible(true);
        });

        searchProductButton.addActionListener(e -> {

            List<MenuItem> menuItems = searchProducts(searchProductField.getText());

            if (menuItems.size() == 0 && !searchProductField.getText().toLowerCase().equals("all")) {
                JOptionPane.showMessageDialog(null, "No such items!");
            } else {
                if (searchProductField.getText().toLowerCase().equals("all")) {

                    List<MenuItem> allMenuItems = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
                    JPanel panel3 = new JPanel();
                    panel3.setLayout(null);

                    initializeAdmin(panel3, allMenuItems);
                    initializeAdminListeners();

                    this.setContentPane(panel3);
                    this.setVisible(true);
                } else {
                    JPanel panel3 = new JPanel();
                    panel3.setLayout(null);

                    initializeAdmin(panel3, menuItems);
                    initializeAdminListeners();

                    this.setContentPane(panel3);
                    this.setVisible(true);
                }
            }
        });

        generateReport1.addActionListener(e -> {

            int minHour, maxHour;
            minHour = Integer.parseInt(hourNumber1.getText());
            maxHour = Integer.parseInt(hourNumber2.getText());
            List<Order> orders = new ArrayList<Order>();

            for (Order order : deliveryService.getOrders()) {
                if (order.getOrderDate().getHours() <= maxHour && order.getOrderDate().getHours() >= minHour) {
                    orders.add(order);
                }
            }

            List<Object> orders1 = new ArrayList<Object>(orders);
            deliveryService.generateReport("1", orders1);

            JOptionPane.showMessageDialog(null, "Report generated!");

            JPanel panel3 = new JPanel();
            panel3.setLayout(null);

            List<MenuItem> allMenuItems = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
            initializeAdmin(panel3, allMenuItems);
            initializeAdminListeners();

            this.setContentPane(panel3);
            this.setVisible(true);
        });

        generateReport2.addActionListener(e -> {

            int minProductOrdered;
            minProductOrdered = Integer.parseInt(productsMoreThan.getText());

            List<MenuItem> products = new ArrayList<MenuItem>();
            for (MenuItem menuItem : deliveryService.getMenuItemList()) {
                int count = 0;
                for (Order order : deliveryService.getOrders()) {
                    for (MenuItem item : order.getMenuItemList()) {
                        if(menuItem.getTitle().equals(item.getTitle())) {
                            count++;
                        }
                    }
                }
                if(count > minProductOrdered) {
                    products.add(menuItem);
                }
            }

            List<Object> products1 = new ArrayList<Object>(products);
            deliveryService.generateReport("2", products1);

            JOptionPane.showMessageDialog(null, "Report generated!");

            JPanel panel3 = new JPanel();
            panel3.setLayout(null);

            List<MenuItem> allMenuItems = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
            initializeAdmin(panel3, allMenuItems);
            initializeAdminListeners();

            this.setContentPane(panel3);
            this.setVisible(true);
        });

        generateReport3.addActionListener(e -> {

            int minClient, minPrice;
            minClient = Integer.parseInt(clientsMoreThan.getText());
            minPrice = Integer.parseInt(priceMoreThan.getText());

            List<Client> clients = new ArrayList<Client>();
            for (Client client : deliveryService.getClients()) {
                int count = 0;
                for (Order order : deliveryService.getOrders()) {
                    if (client.getId() == order.getClientID()) {
                        int price = 0;
                        for (MenuItem menuItem : order.getMenuItemList()) {
                            price = price + menuItem.computePrice();
                        }
                        if (price > minPrice) {
                            count++;
                        }
                    }
                }
                if(count > minClient) {
                    clients.add(client);
                }
            }

            List<Object> clients1 = new ArrayList<Object>(clients);
            deliveryService.generateReport("3", clients1);

            JOptionPane.showMessageDialog(null, "Report generated!");

            JPanel panel3 = new JPanel();
            panel3.setLayout(null);

            List<MenuItem> allMenuItems = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
            initializeAdmin(panel3, allMenuItems);
            initializeAdminListeners();

            this.setContentPane(panel3);
            this.setVisible(true);
        });

        generateReport4.addActionListener(e -> {

            String[] weekDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

            String day = (String) weekdayBox.getSelectedItem();
            int i = 0;
            int dayNumber = 0;
            for (String weekDay : weekDays) {
                if (weekDay.equals(day)) {
                    dayNumber = i;
                }
                i++;
            }

            List<MenuItem> products = new ArrayList<MenuItem>();
            Integer[] timesOrdered = new Integer[deliveryService.getMenuItemList().size()];
            i = 0;
            for (MenuItem menuItem : deliveryService.getMenuItemList()) {
                int count = 0;
                for (Order order : deliveryService.getOrders()) {
                    if (order.getOrderDate().getDay() == dayNumber) {
                        for (MenuItem item : order.getMenuItemList()) {
                            if (menuItem.getTitle().equals(item.getTitle())) {
                                count++;
                            }
                        }
                    }
                }
                if (count > 0) {
                    products.add(menuItem);
                    timesOrdered[i] = count;
                    i++;
                }
            }

            List<Object> products1 = new ArrayList<Object>(products);
            deliveryService.generateMapReport("4", timesOrdered, products1);

            JOptionPane.showMessageDialog(null, "Report generated!");

            JPanel panel3 = new JPanel();
            panel3.setLayout(null);

            List<MenuItem> allMenuItems = new ArrayList<MenuItem>(deliveryService.getMenuItemList());
            initializeAdmin(panel3, allMenuItems);
            initializeAdminListeners();

            this.setContentPane(panel3);
            this.setVisible(true);
        });
    }

    /**
     * Method that adds the necessary elements to the employee panel
     * @param panel - the employee panel
     * */
    private void initializeEmployee(JPanel panel) {

        JLabel titleLabel = new JLabel("Employee page");
        titleLabel.setBounds(270, 35, 200, 30);

        logOutButton = new JButton("LOG OUT");
        logOutButton.setBounds(30, 35, 100, 30);

        //List<Order> orders = new ArrayList<Order>(deliveryService.getOrders());
        dbTable = menuItemTableManager.fillOrdersTable(deliveryService.getOrderCollectionMap());
        dbTable.setVisible(true);
        dbTable.setEnabled(true);

        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(30, 100, 450, 250);
        scrollPane2.setViewportView(dbTable);

        markAsDoneButton = new JButton("MARK AS DONE");
        markAsDoneButton.setBounds(30, 360, 150, 30);

        panel.add(titleLabel);
        panel.add(logOutButton);
        panel.add(scrollPane2);
        panel.add(markAsDoneButton);
    }

    /**
     * Method that contains listeners for the employee panel's buttons
     * */
    private void initializeEmployeeListeners() {

        logOutButton.addActionListener(e -> {
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            initializeLogin(panel1);
            initializeLoginListeners();

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        final Order[] order = {new Order(0, 0, null, null)};
        final Collection<MenuItem>[] menuItems = new Collection[]{new ArrayList<MenuItem>()};

        dbTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                for (Map.Entry<Order, Collection<MenuItem>> orderCollectionEntry : deliveryService.getOrderCollectionMap().entrySet()) {
                    if(orderCollectionEntry.getKey().getOrderID() == Integer.parseInt(dbTable.getValueAt(dbTable.getSelectedRow(), 0).toString())) {
                        order[0] = orderCollectionEntry.getKey();
                        menuItems[0] = orderCollectionEntry.getValue();
                    }
                }
            }
        });

        markAsDoneButton.addActionListener(e -> {
            deliveryService.doOrder(order[order.length - 1], menuItems[menuItems.length - 1]);

            JPanel panel3 = new JPanel();
            panel3.setLayout(null);

            initializeEmployee(panel3);
            initializeEmployeeListeners();

            this.setContentPane(panel3);
            this.setVisible(true);
        });
    }

    /**
     * Method that adds the necessary elements to the client panel
     * @param panel - the main panel
     * */
    private void initializeClient(JPanel panel, List<MenuItem> menuItems) {

        JLabel titleLabel = new JLabel("Client page");
        titleLabel.setBounds(270, 35, 200, 30);

        logOutButton = new JButton("LOG OUT");
        logOutButton.setBounds(30, 35, 100, 30);

        Collections.sort(menuItems, Comparator.comparing(MenuItem::getTitle));
        dbTable = menuItemTableManager.fillMenuTable(menuItems);
        dbTable.setVisible(true);
        dbTable.setEnabled(true);

        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(30, 100, 450, 250);
        scrollPane2.setViewportView(dbTable);

        searchProductField = new JTextField();
        searchProductField.setBounds(440, 35, 100, 30);

        searchProductButton = new JButton("SEARCH");
        searchProductButton.setBounds(550, 35, 100, 30);

        addProductButton = new JButton("ADD");
        addProductButton.setBounds(500, 100, 100, 30);

        orderButton = new JButton("ORDER");
        orderButton.setBounds(500, 140, 100, 30);

        panel.add(titleLabel);
        panel.add(logOutButton);
        panel.add(scrollPane2);
        panel.add(searchProductField);
        panel.add(searchProductButton);
        panel.add(addProductButton);
        panel.add(orderButton);
    }

    /**
     * Method that contains listeners for the client panel's buttons
     * */
    private void initializeClientListeners() {

        logOutButton.addActionListener(e -> {
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            initializeLogin(panel1);
            initializeLoginListeners();

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        searchProductButton.addActionListener(e -> {

            List<MenuItem> menuItems = searchProducts(searchProductField.getText());

            if (menuItems.size() == 0 && !searchProductField.getText().toLowerCase().equals("all")) {
                JOptionPane.showMessageDialog(null, "No such items!");
            } else {
                if (searchProductField.getText().toLowerCase().equals("all")) {
                    List<MenuItem> allMenuItems = new ArrayList<MenuItem>(deliveryService.getMenuItemList());

                    JPanel panel3 = new JPanel();
                    panel3.setLayout(null);

                    initializeClient(panel3, allMenuItems);
                    initializeClientListeners();

                    this.setContentPane(panel3);
                    this.setVisible(true);
                } else {
                    JPanel panel3 = new JPanel();
                    panel3.setLayout(null);

                    initializeClient(panel3, menuItems);
                    initializeClientListeners();

                    this.setContentPane(panel3);
                    this.setVisible(true);
                }
            }
        });

        List<MenuItem> menuItems2 = new ArrayList<MenuItem>();

        dbTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                HashSet<MenuItem> menuItems1 = deliveryService.getMenuItemList();
                for (MenuItem menuItem : menuItems1) {
                    if (menuItem.getTitle().equals(dbTable.getValueAt(dbTable.getSelectedRow(), 0).toString())) {
                        addProductButton.addActionListener(e -> {
                            menuItems2.add(menuItem);
                        });
                    }
                }
            }
        });

        orderButton.addActionListener(e -> {
            List<MenuItem> newList = new ArrayList<MenuItem>();
            for (MenuItem element : menuItems2) {

                if (!newList.contains(element)) {
                    newList.add(element);
                }
            }
            deliveryService.createOrder(currentClient, newList);
            JOptionPane.showMessageDialog(null, "Bill generated!");
        });
    }

    private List<MenuItem> searchProducts(String keyWord){
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        deliveryService.getMenuItemList().forEach(o -> {
            if(o.getTitle().toLowerCase().contains(keyWord.toLowerCase())) {
                menuItems.add(o);
            }
        });
        return menuItems;
    }
}