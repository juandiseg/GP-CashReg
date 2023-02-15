package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import objects.*;

import java.sql.ResultSet;

public class managerDB {

    private final static String url = "jdbc:mysql://localhost:3306/beatneat";
    private final String user = "isabel"; // Change to your local user
    private final String password = "Isabel"; // Change to your local password

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

    public ArrayList<orderItems> getOrderItems(int order_id) {
        ArrayList<orderItems> tempList = new ArrayList<orderItems>();
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
                    tempList.add(new orderItems(order_id, (new product(product_id, name, price, active)), quantity));
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
                JOptionPane.showMessageDialog(null, "No username found", "Error",
                            JOptionPane.WARNING_MESSAGE);
                return 0;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public ArrayList<category> getAllCategories() {
        ArrayList<category> tempList = new ArrayList<category>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT category_id, category_name FROM categories";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int categoryID = rs.getInt("category_id");
                    String name = rs.getString("category_name");
                    if (getProductsByCategory(categoryID).size() != 0) {
                        tempList.add(new category(categoryID, name));
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

    public ArrayList<product> getProductsByCategory(int category_id) {
        ArrayList<product> tempList = new ArrayList<product>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT products.product_id, products.name, products.price, products.active FROM products " + 
            "INNER JOIN products_categories ON products.product_id = products_categories.product_id " +
            "INNER JOIN categories ON products_categories.category_id = categories.category_id " + 
            "WHERE categories.category_id = " + category_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int product_id = rs.getInt("products.product_id");
                    String name = rs.getString("products.name");
                    float price = rs.getFloat("products.price");
                    Boolean active = rs.getBoolean("products.active");
                    tempList.add(new product(product_id, name, price, active));
                }
                // System.out.println(tempList);
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

    public void checkIn(int employee_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE employees_schedule SET realtime_in = '" + java.time.LocalTime.now() +
                "' WHERE employee_id = " + employee_id + " AND shift_date = '" + java.time.LocalDate.now() + "';";
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

    public void checkOut(int employee_id, String realtime_out, Boolean undertime) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE employees_schedule SET realtime_out = '" + realtime_out + "', undertime = " + undertime + 
            " WHERE employee_id = " + employee_id + " AND shift_date = '" + java.time.LocalDate.now() + "';";
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

    public employee_schedule getEmployee_schedules(int employee_id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM employees_schedule;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    employee_id = rs.getInt("employee_id");
                    String date = rs.getString("shift_date");
                    String start_shift = rs.getString("start_shift");
                    String end_shift = rs.getString("end_shift");
                    String realtime_in = rs.getString("realtime_in");
                    String realtime_out = rs.getString("realtime_out");
                    Boolean undertime = rs.getBoolean("undertime");
                    connection.close();
                    return new employee_schedule(employee_id, date, start_shift, end_shift, realtime_in, realtime_out, undertime);
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
}