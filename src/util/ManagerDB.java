package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import objects.*;

import java.sql.ResultSet;

public class ManagerDB {

    private final static String url = "jdbc:mysql://localhost:3306/beatneat";
    private final String user = "isabel"; // Change to your local user
    private final String password = "Isabel"; // Change to your local password

    public ArrayList<Table> getTables() {
        ArrayList<Table> tempList = new ArrayList<Table>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM tables;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int table_id = rs.getInt("table_id");
                    int order_id = rs.getInt("order_id");
                    Boolean is_empty = rs.getBoolean("is_empty");
                    tempList.add(new Table(table_id, order_id, is_empty));
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

    public ArrayList<OrderItems> getOrderItems(int order_id) {
        ArrayList<OrderItems> tempList = new ArrayList<OrderItems>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT products.product_id, products.name, products.price, products.active, orders_items.quantity FROM products INNER JOIN orders_items ON products.product_id = orders_items.product_id WHERE orders_items.order_id = " + order_id + ";";
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

    public ArrayList<OrderMenus> getOrderMenus(int order_id) {
        ArrayList<OrderMenus> tempList = new ArrayList<OrderMenus>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT menus.menu_id, menus.name, menus.price, menus.active, orders_menus.quantity FROM menus INNER JOIN orders_menus ON menus.menu_id = orders_menus.menu_id WHERE orders_menus.order_id = " + order_id + ";";
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

    public int getOrderID(int table_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT order_id FROM tables WHERE table_id =  " + table_id + ";";
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
    
    public int checkEmployeeID(int employee_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT employee_id FROM employees WHERE employee_id =  " + employee_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                connection.close();
                return 1;
                }
                JOptionPane.showMessageDialog(null, "No matching ID found", "Error", JOptionPane.ERROR_MESSAGE);
                return 0;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

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

    public ArrayList<Product> getProductsByCategory(int category_id) {
        ArrayList<Product> tempList = new ArrayList<Product>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT products.product_id, products.name, products.price, products.active FROM products " + 
            "INNER JOIN categories ON products.category_id = categories.category_id " + 
            "WHERE categories.category_id = " + category_id + " AND products.active = true AND categories.iscategory_product = true;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int product_id = rs.getInt("products.product_id");
                    String name = rs.getString("products.name");
                    float price = rs.getFloat("products.price");
                    Boolean active = rs.getBoolean("products.active");
                    tempList.add(new Product(product_id, name, price, active));
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

    public ArrayList<Menu> getMenusByCategory(int category_id) {
        ArrayList<Menu> tempList = new ArrayList<Menu>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT menus.menu_id, menus.name, menus.price, menus.active FROM menus " + 
            "INNER JOIN categories ON menus.category_id = categories.category_id " + 
            "WHERE categories.category_id = " + category_id + " AND menus.active = true AND categories.iscategory_product = false;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int menu_id = rs.getInt("menus.menu_id");
                    String name = rs.getString("menus.name");
                    float price = rs.getFloat("menus.price");
                    Boolean active = rs.getBoolean("menus.active");
                    tempList.add(new Menu(menu_id, name, price, active));
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

    public void addNewOrder(int order_id, int product_id) {
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

    public void makeTableOccupied(int order_id, int table_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE tables SET order_id = " + order_id +", is_empty = 0 WHERE table_id = " + table_id +";";
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

    public void makeTableEmpty(int table_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE tables SET order_id = NULL, is_empty = 1 WHERE table_id = " + table_id +";";
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

    public boolean checkProductInOrder (int product_id, int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT product_id FROM orders_items WHERE product_id =  " + product_id + " AND order_id = " + order_id + ";";
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

    public boolean checkMenuInOrder (int menu_id, int order_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT menu_id FROM orders_menus WHERE menu_id =  " + menu_id + " AND order_id = " + order_id + ";";
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

    public void updateProductQuantity(int order_id, int product_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE orders_items SET quantity = (1+" + getProductQuantity(order_id, product_id) + ") WHERE order_id = " + 
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

    public void updateMenuQuantity(int order_id, int menu_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE orders_menus SET quantity = (1+" + getMenuQuantity(order_id, menu_id) + ") WHERE order_id = " + 
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

    private int getProductQuantity(int order_id, int product_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT quantity FROM orders_items WHERE order_id = " + order_id + " AND product_id = " + product_id + ";";
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

    private int getMenuQuantity(int order_id, int menu_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT quantity FROM orders_menus WHERE order_id = " + order_id + " AND menu_id = " + menu_id + ";";
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

    public void checkOutOrder(int order_id, float total, float tax, float subtotal, Boolean cash) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE orders_summary SET time_out = '" + java.time.LocalTime.now() + "', total = " + total + 
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

    public boolean checkOut(int employee_id, String realtime_out, Boolean undertime) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE employees_schedule SET realtime_out = '" + realtime_out + "', undertime = " + undertime + 
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

    public EmployeeShift getEmployee_shift(int employee_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM employees_schedule WHERE employee_id = " + employee_id + " AND shift_date = '" + java.time.LocalDate.now() + "';";
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
                    return new EmployeeShift(employee_id, date, start_shift, end_shift, realtime_in, realtime_out, undertime);
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

    public void deleteOrderItem(int order_id, int product_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM orders_items WHERE order_id = " + order_id + " AND product_id = " + product_id + ";";
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

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> tempList = new ArrayList<Product>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM products;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int product_id = rs.getInt("product_id");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    Boolean active = rs.getBoolean("active");
                    tempList.add(new Product(product_id, name, price, active));
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

    public ArrayList<Menu> getAllMenus() {
        ArrayList<Menu> tempList = new ArrayList<Menu>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM menus;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int menu_id = rs.getInt("menu_id");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    Boolean active = rs.getBoolean("active");
                    tempList.add(new Menu(menu_id, name, price, active));
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

    public void updateProductAvailability(int product_id, Boolean active) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE products SET active = "+ active + " WHERE product_id = " + product_id + ";";
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

    public void updateMenuAvailability(int menu_id, Boolean active) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE menus SET active = "+ active + " WHERE menu_id = " + menu_id + ";";
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