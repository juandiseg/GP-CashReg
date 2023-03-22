package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import objects.*;

import java.sql.ResultSet;

// Class to interact with the database, creates queries to execute and extract or modify data
public class ManagerDB {

    private final static String url = "jdbc:mysql://localhost:3306/beatneat";
    private final static String user = "isabel"; // Change to your local user
    private final static String password = "Isabel"; // Change to your local password

    /**
     * To get all the tables IDs
     * 
     * @return an array list with all the IDs with the data type integer
     */
    public ArrayList<Integer> getTablesIDs() {
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM tables;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int table_id = rs.getInt("table_id");
                    tempList.add(table_id);
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get the total number of tables
     * 
     * @return an integer with the number of tables
     */
    public int getAmountOfTables() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT COUNT(table_id) as numberTables FROM tables;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int count = rs.getInt("numberTables");
                    connection.close();
                    return count;
                } else {
                    connection.close();
                    return -1;
                }
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get if a table is empty or occupied
     * 
     * @param table_id the table ID that we want to check if it is free or not
     * @return and integer that says the status of the table: 1 empty, 0 occupied
     */
    public int getTableStatus(int table_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT is_empty FROM tables WHERE table_id = " + table_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int is_empty = rs.getInt("is_empty");
                    connection.close();
                    return is_empty;
                } else {
                    connection.close();
                    return -1;
                }
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get all the IDs of only the free tables
     * 
     * @return an array list with all the free tables IDs
     */
    public ArrayList<Integer> getFreeTables() {
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT table_id FROM tables WHERE is_empty = 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int table_id = rs.getInt("table_id");
                    tempList.add(table_id);
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get all the IDs of only the occupied tables
     * 
     * @return an array list with all the occupied tables IDs
     */
    public ArrayList<Integer> getOccupiedTables() {
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT table_id FROM tables WHERE is_empty = 0;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int table_id = rs.getInt("table_id");
                    tempList.add(table_id);
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get all the items of an order
     * 
     * @param order_id the order ID that we want the items from
     * @return an array list with all the order's items
     */
    public ArrayList<OrderItems> getOrderItems(int order_id) {
        ArrayList<OrderItems> tempList = new ArrayList<OrderItems>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT products.product_id, products.name, products.price, products.active, orders_items.quantity FROM products INNER JOIN orders_items "
                    +
                    "ON products.product_id = orders_items.product_id WHERE orders_items.order_id = " + order_id
                    + " AND active = 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int product_id = rs.getInt("products.product_id");
                    String name = rs.getString("products.name");
                    float price = rs.getFloat("products.price");
                    Boolean active = rs.getBoolean("products.active");
                    int quantity = rs.getInt("orders_items.quantity");
                    tempList.add(new OrderItems(order_id, (new Product(product_id, name, price, active)), quantity));
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get all the menus items of an order
     * 
     * @param order_id the order ID that we want the menus items from
     * @return an array list with all the order's menus items
     */
    public ArrayList<OrderMenus> getOrderMenus(int order_id) {
        ArrayList<OrderMenus> tempList = new ArrayList<OrderMenus>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT menus.menu_id, menus.name, menus.price, menus.active, orders_menus.quantity FROM menus INNER JOIN orders_menus ON menus.menu_id = orders_menus.menu_id "
                    +
                    "WHERE orders_menus.order_id = " + order_id + " AND active = 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int menu_id = rs.getInt("menus.menu_id");
                    String name = rs.getString("menus.name");
                    float price = rs.getFloat("menus.price");
                    Boolean active = rs.getBoolean("menus.active");
                    int quantity = rs.getInt("orders_menus.quantity");
                    tempList.add(new OrderMenus(order_id, (new Menu(menu_id, name, price, active)), quantity));
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get the ID of the last order
     * 
     * @return an integer witih the last order ID
     */
    public int getLastOrderID() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT order_id FROM orders_summary ORDER BY order_id DESC LIMIT 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int orderID = rs.getInt("order_id");
                    connection.close();
                    return orderID;
                }
                return -1;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get the orders IDs
     * 
     * @return an array list with the orders IDs
     */
    public ArrayList<Integer> getOrders() {
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT order_id FROM orders_summary WHERE time_out IS NULL;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int order_id = rs.getInt("order_id");
                    tempList.add(order_id);
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get the order ID of a table
     * 
     * @param table_id the table ID from where we want to get the order from
     * @return an integer with the order ID, if the table doesn't have an order it will return -1
     */
    public int getOrderID(int table_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT order_id FROM tables WHERE table_id =  " + table_id + " AND order_id IS NOT NULL;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int order_id = rs.getInt("order_id");
                    connection.close();
                    return order_id;
                }
                return -1;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To check if an employee ID exists
     * 
     * @param employee_id employee ID we want to check
     * @return a boolean representing if exists or not: true if it does, false if it doesn't
     */
    public Boolean checkEmployeeID(int employee_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT employee_id FROM employees WHERE employee_id =  " + employee_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    connection.close();
                    return true;
                }
                JOptionPane.showMessageDialog(null, "No matching ID found", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get all the products' categories
     * 
     * @return an array list with the categeories
     */
    public ArrayList<Category> getAllCategoriesProducts() {
        ArrayList<Category> tempList = new ArrayList<Category>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT category_id, category_name FROM categories WHERE iscategory_product = 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int categoryID = rs.getInt("category_id");
                    String name = rs.getString("category_name");
                    if (getProductsByCategory(categoryID).size() != 0) {
                        tempList.add(new Category(categoryID, name));
                    }
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get all the menus' categories
     * 
     * @return an array list with the categeories
     */
    public ArrayList<Category> getAllCategoriesMenus() {
        ArrayList<Category> tempList = new ArrayList<Category>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT category_id, category_name FROM categories WHERE iscategory_product = 0;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int categoryID = rs.getInt("category_id");
                    String name = rs.getString("category_name");
                    if (getMenusByCategory(categoryID).size() != 0) {
                        tempList.add(new Category(categoryID, name));
                    }
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get all the items a product category has
     * 
     * @param category_id the category ID from where we want to get the items from
     * @return an array list with all the products
     */
    public ArrayList<Product> getProductsByCategory(int category_id) {
        ArrayList<Product> tempList = new ArrayList<Product>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT products.product_id, products.name, products.price, products.active FROM products " +
                    "INNER JOIN categories ON products.category_id = categories.category_id " +
                    "WHERE categories.category_id = " + category_id
                    + " AND products.active = true AND categories.iscategory_product = true;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int product_id = rs.getInt("products.product_id");
                    String name = rs.getString("products.name");
                    float price = rs.getFloat("products.price");
                    Boolean active = rs.getBoolean("products.active");
                    Product temp = new Product(product_id, name, price, active);
                    if (areIngredientsInInventory(temp))
                        tempList.add(temp);
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To check if the ingredients of a product are available. Basically if the product exists
     * 
     * @param theProduct the product we are checking the ingredients
     * @return a boolean indicating if the product is available or not
     */
    private boolean areIngredientsInInventory(Product theProduct) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT 1 FROM products_ingredients NATURAL JOIN ingredients WHERE product_ingredients_date = (SELECT MAX(product_ingredients_date) FROM products_ingredients WHERE product_id = "
                    + theProduct.getID() + ") AND product_id = " + theProduct.getID() + " AND in_inventory = false;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next())
                    return false;
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To check if the ingredients of a product are available and therefore the menu
     * 
     * @param theMenu the me we are checking
     * @return  boolean indicating if the menu is available or not
     */
    private boolean areIngredientsInInventory(Menu theMenu) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT 1 FROM products_ingredients NATURAL JOIN ingredients WHERE product_ingredients_date = (SELECT MAX(product_ingredients_date) FROM products_ingredients WHERE product_id IN (SELECT product_id FROM menus_products WHERE menu_products_date = (SELECT MAX(menu_products_date) FROM menus_products WHERE menu_id = "
                    + theMenu.getId() + ") AND menu_id = " + theMenu.getId()
                    + ")) AND product_id IN (SELECT product_id FROM menus_products WHERE menu_products_date = (SELECT MAX(menu_products_date) FROM menus_products WHERE menu_id = "
                    + theMenu.getId() + ") AND menu_id = " + theMenu.getId() + ") AND in_inventory = false;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next())
                    return false;
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get all the items a menu category has
     * 
     * @param category_id the category ID from where we want to get the items from
     * @return an array list with all the menus
     */
    public ArrayList<Menu> getMenusByCategory(int category_id) {
        ArrayList<Menu> tempList = new ArrayList<Menu>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT menus.menu_id, menus.name, menus.price, menus.active FROM menus " +
                    "INNER JOIN categories ON menus.category_id = categories.category_id " +
                    "WHERE categories.category_id = " + category_id
                    + " AND menus.active = true AND categories.iscategory_product = false;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int menu_id = rs.getInt("menus.menu_id");
                    String name = rs.getString("menus.name");
                    float price = rs.getFloat("menus.price");
                    Boolean active = rs.getBoolean("menus.active");
                    Menu temp = new Menu(menu_id, name, price, active);
                    if (areIngredientsInInventory(temp))
                        tempList.add(temp);
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To check if an order exists or not
     * 
     * @param order_id the order ID we want to check
     * @return a boolean representinf if the order exists: true it doesn't exist so it's a new order, false it exists
     */
    public boolean newOrder(int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT order_id FROM orders_summary WHERE order_id =  " + order_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    connection.close();
                    return false;
                }
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return true;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To create a new order, putting the order ID, time and date, the rest are nulls because we still don't have that information
     * 
     * @param order_id the order ID of the new order we are creating
     */
    public void addNewOrder(int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO orders_summary VALUES (" + order_id + ", '" + java.time.LocalTime.now() +
                    "', NULL, NULL, NULL, NULL, '" + java.time.LocalDate.now() + "', NULL);";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To add product items into an order
     * 
     * @param order_id the order ID we are adding products to
     * @param product_id the product ID we want to add to the order
     */
    public void addOrderItem(int order_id, int product_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO orders_items VALUES (" + order_id + ", " + product_id + ", 1);";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To add menu items into an order
     * 
     * @param order_id the order ID we are adding menus to
     * @param menu_id the menu ID we want to add to the order
     */
    public void addOrderMenu(int order_id, int menu_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO orders_menus VALUES (" + order_id + ", " + menu_id + ", 1);";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get the table ID where an order is
     * 
     * @param order_id the order ID we are checking the table for
     * @return an integer with the table ID, if the order is not in a table then it returns -1
     */
    public int getTableID(int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT table_id FROM tables WHERE order_id =  " + order_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int table_id = rs.getInt("table_id");
                    connection.close();
                    return table_id;
                }
                return -1;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To make a table occupied, changing in the table tables in the database the column is_empty to 0 and adding the order
     * 
     * @param order_id order ID the table will have
     * @param table_id table ID of the table we want to change the information of
     */
    public void makeTableOccupied(int order_id, int table_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE tables SET order_id = " + order_id + ", is_empty = 0 WHERE table_id = " + table_id
                    + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To make a table empty, by deleting the order ID (null) and setting is_empty to 1
     * 
     * @param table_id table ID of the table we want to change the information of
     */
    public void makeTableEmpty(int table_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE tables SET order_id = NULL, is_empty = 1 WHERE table_id = " + table_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To check if a product is in an order
     * 
     * @param product_id the product ID we want to check
     * @param order_id the order ID we want to check
     * @return a boolean representing if the product is in the order or not: true it does, false it doesn't
     */
    public boolean checkProductInOrder(int product_id, int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT product_id FROM orders_items WHERE product_id =  " + product_id + " AND order_id = "
                    + order_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    connection.close();
                    return true;
                }
                return false;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To check if a menu is in an order
     * 
     * @param menu_id the menu ID we want to check
     * @param order_id the order ID we want to check
     * @return a boolean representing if the menu is in the order or not: true it does, false it doesn't
     */
    public boolean checkMenuInOrder(int menu_id, int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT menu_id FROM orders_menus WHERE menu_id =  " + menu_id + " AND order_id = "
                    + order_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    connection.close();
                    return true;
                }
                return false;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To make the product's quantity of an order one more
     * 
     * @param order_id the order ID we want to change
     * @param product_id the product ID we want to chenge the quantity of
     */
    public void updateProductQuantity(int order_id, int product_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE orders_items SET quantity = (1+" + getProductQuantity(order_id, product_id)
                    + ") WHERE order_id = " +
                    order_id + " AND product_id = " + product_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To make the menu's quantity of an order one more
     * 
     * @param order_id the order ID we want to change
     * @param menu_id the menu ID we want to chenge the quantity of
     */
    public void updateMenuQuantity(int order_id, int menu_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE orders_menus SET quantity = (1+" + getMenuQuantity(order_id, menu_id)
                    + ") WHERE order_id = " +
                    order_id + " AND menu_id = " + menu_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To change the product's quantity of an order
     * 
     * @param order_id order ID we want to change
     * @param product_id product ID we want to change the quantity of
     * @param quantity the quantity we want to change to
     */
    public void setProductQuantity(int order_id, int product_id, int quantity) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE orders_items SET quantity = " + quantity + " WHERE order_id = " +
                    order_id + " AND product_id = " + product_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To change the menu's quantity of an order
     * 
     * @param order_id order ID we want to change
     * @param menu_id menu ID we want to change the quantity of
     * @param quantity the quantity we want to change to
     */
    public void setMenuQuantity(int order_id, int menu_id, int quantity) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE orders_menus SET quantity = " + quantity + " WHERE order_id = " +
                    order_id + " AND menu_id = " + menu_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get a product's quantity in an order
     * 
     * @param order_id order ID we want to check
     * @param product_id product ID we want to check the quantity of
     * @return an integer with the product's quantity
     */
    private int getProductQuantity(int order_id, int product_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT quantity FROM orders_items WHERE order_id = " + order_id + " AND product_id = "
                    + product_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    connection.close();
                    return quantity;
                }
                return -1;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get a menu's quantity in an order
     * 
     * @param order_id order ID we want to check
     * @param menu_id menu ID we want to check the quantity of
     * @return an integer with the menu's quantity
     */
    private int getMenuQuantity(int order_id, int menu_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT quantity FROM orders_menus WHERE order_id = " + order_id + " AND menu_id = "
                    + menu_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    connection.close();
                    return quantity;
                }
                return -1;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To check out an order (pay)
     * 
     * @param order_id order ID we want to check out
     * @param total the total price of the order
     * @param tax the tax the order has
     * @param subtotal the subtotal of the order
     * @param cash if you have paidi with cash or not (card)
     */
    public void checkOutOrder(int order_id, float total, float tax, float subtotal, Boolean cash) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE orders_summary SET time_out = '" + java.time.LocalTime.now() + "', total = " + total
                    +
                    ", tax = " + tax + ", subtotal = " + subtotal + ", cash = " + cash +
                    " WHERE order_id = " + order_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To let an employee check-in
     * 
     * @param employee_id employee ID that wants to check in
     * @return a boolean saying if the check in has been successful or not
     */
    public boolean checkIn(int employee_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE employees_schedule SET realtime_in = '" + java.time.LocalTime.now() +
                    "' WHERE employee_id = " + employee_id + " AND shift_date = '" + java.time.LocalDate.now() + "';";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To let an employee check-out
     * 
     * @param employee_id employee ID that wants to check out
     * @param realtime_out the time the employee checked out
     * @param undertime if the employee has worked less hours than what he had to
     * @return a boolean saying if the check out has been successful or not
     */
    public boolean checkOut(int employee_id, String realtime_out, Boolean undertime) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE employees_schedule SET realtime_out = '" + realtime_out + "', undertime = "
                    + undertime +
                    " WHERE employee_id = " + employee_id + " AND shift_date = '" + java.time.LocalDate.now() + "';";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To get an employee's shift
     * 
     * @param employee_id the meployee's ID we want to get the shift for
     * @return the employee's shift
     */
    public EmployeeShift getEmployee_shift(int employee_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM employees_schedule WHERE employee_id = " + employee_id + " AND shift_date = '"
                    + java.time.LocalDate.now() + "';";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String date = rs.getString("shift_date");
                    String start_shift = rs.getString("start_shift");
                    String end_shift = rs.getString("end_shift");
                    String realtime_in = rs.getString("realtime_in");
                    String realtime_out = rs.getString("realtime_out");
                    Boolean undertime = rs.getBoolean("undertime");
                    connection.close();
                    return new EmployeeShift(employee_id, date, start_shift, end_shift, realtime_in, realtime_out,
                            undertime);
                }
                return null;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To delete all the product's items of an order
     * 
     * @param order_id order ID we want to delete the products from
     */
    public void deleteAllOrderItems(int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM orders_items WHERE order_id = " + order_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To delete all the menu's items of an order
     * 
     * @param order_id order ID we want to delete the menus from
     */
    public void deleteAllOrderMenus(int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM orders_menus WHERE order_id = " + order_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To delete an order summary
     * 
     * @param order_id order ID we want to delete
     */
    public void deleteOrderSummary(int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM orders_summary WHERE order_id = " + order_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To delete a product's item from an order
     * 
     * @param order_id order ID we want to delete the product from
     * @param product_id product ID we want to delete from the order
     */
    public void deleteOrderItem(int order_id, int product_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM orders_items WHERE order_id = " + order_id + " AND product_id = " + product_id
                    + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To delete menu's item from an order
     * 
     * @param order_id order ID we want to delete the menu from
     * @param menu_id menu ID we want to delete from the order
     */
    public void deleteOrderMenu(int order_id, int menu_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM orders_menus WHERE order_id = " + order_id + " AND menu_id = " + menu_id + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * TO get all the ingredients in the inventory
     * 
     * @return an array list with all the ingredients
     */
    public ArrayList<Ingredient> getAllIngredients() {
        ArrayList<Ingredient> tempList = new ArrayList<Ingredient>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM ingredients WHERE active = 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ingredientID = rs.getInt("ingredient_id");
                    String date = rs.getString("ingredients_date");
                    String name = rs.getString("name");
                    Boolean inventory = rs.getBoolean("in_inventory");
                    tempList.add(new Ingredient(ingredientID, date, name, inventory));
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * To change the availability of an ingredient (if it's in the inventory or not)
     * 
     * @param theIngredient the ingredient we want to change
     * @param inventory if the ingredient is in the inventory or not
     */
    public void updateIngredientInventory(Ingredient theIngredient, Boolean inventory) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE ingredients SET in_inventory = " + inventory + " WHERE ingredient_id = "
                    + theIngredient.getId()
                    + " AND ingredients_date = '" + theIngredient.getDate() + "';";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}