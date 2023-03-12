package util;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import objects.Menu;
import objects.OrderItems;
import objects.OrderMenus;
import objects.Product;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class to create table where you can see the summary of an especific order
public class TableInput {

    private JTable t = new JTable() {
        public boolean isCellEditable(int row, int column){  
            return false;  
        }
    };
    private DefaultTableModel model = new DefaultTableModel(new String[] { "Product", "Quantity" }, 0);
    private int order_id;
    private ArrayList<OrderItems> orderItems;
    private ArrayList<OrderMenus> orderMenus;
    private ManagerDB theManagerDB = new ManagerDB(); 
    
    /**
     * Constructor of AbstractTable
     * 
     * @param thePanel panel where the table will go
     * @param order_id integer representing the order ID
     * @param orderItems array of the items in the order
     * @param orderMenus array of the menus in the order
     */
    public TableInput(JPanel thePanel, int order_id, ArrayList<OrderItems> orderItems, ArrayList<OrderMenus> orderMenus) {
        this.order_id = order_id;
        this.orderItems = orderItems;
        this.orderMenus = orderMenus;

        JPanel labelPanel = new JPanel();
        JPanel tablePanel = new JPanel();
        JLabel label = new JLabel("Order: " + order_id);
        if (order_id != 0) label.setText("Order: " + order_id);
        else label.setText("Empty Table");

        thePanel.setBorder(null);
        thePanel.setSize(thePanel.getPreferredSize());
        thePanel.setLayout(new BorderLayout());
        tablePanel.setLayout(new GridLayout());

        for (OrderItems item : orderItems) {
            model.addRow(new String[] {item.getName(), Integer.toString(item.getQuantity())});
        }

        for (OrderMenus menus : orderMenus) {
            model.addRow(new String[] {menus.getName(), Integer.toString(menus.getQuantity())});
        }

        t.setModel(model);
        t.setRowSelectionAllowed(false);

        JScrollPane sp = new JScrollPane(t);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        labelPanel.add(label);
        tablePanel.add(sp);

        thePanel.add(labelPanel, BorderLayout.NORTH);
        thePanel.add(tablePanel, BorderLayout.AFTER_LINE_ENDS);
    }


    /**
     * Method that makes the table clickable
     * If it's clickable you will be able to change the quantity of the products or delete them
     * It will ask what you want to do thanks to a panel
     */
    public void clickableTable() {
        t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = t.getSelectedRow();
                Object[] options = {"Edit Quantity", "Delete Product"};
                int n = JOptionPane.showOptionDialog(null, "What do you want to do?", null,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, t);

                if (n == 0) changeQuantity(row);
                else if (n == 1) deleteRow(row);
            }
        });
    }

    /**
     * Method to change the quantity of a product to a given one 
     * For that we create a whole new frame where we will be able to choose the new quantity
     * 
     * @param row the row where the quantity we want to change is
     */
    private void changeQuantity(int row) {

        JFrame frame = new JFrame();
        frame.setSize(210, 150);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
         
        JPanel panel1 = new JPanel();
        JButton button1 = new JButton("-");
        JTextField textField = new JTextField(String.valueOf(t.getValueAt(row, 1)), 5);
        textField.setHorizontalAlignment(JTextField.CENTER);
        JButton button2 = new JButton("+");
        panel1.add(button1);
        panel1.add(textField);
        panel1.add(button2);

        JPanel panel2 = new JPanel();
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");
        panel2.add(okButton);
        panel2.add(cancelButton);

        JLabel label = new JLabel("Specify quantity");
        label.setHorizontalAlignment(JTextField.CENTER);
         
        frame.add(label, BorderLayout.NORTH);
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.SOUTH);
        
        frame.setVisible(true);

        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int temp = Integer.parseInt(textField.getText()) - 1;
                if (temp > 0) {
                    textField.setText(Integer.toString(temp));
                    panel1.revalidate();
                    panel1.repaint();
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int temp = Integer.parseInt(textField.getText()) + 1;
                textField.setText(Integer.toString(temp));
                panel1.revalidate();
                panel1.repaint();
            }
        });
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int temp = Integer.parseInt(textField.getText());
                
                for (OrderItems item : orderItems) {
                    if (item.getName() == String.valueOf(t.getValueAt(row, 0))) {
                        item.setQuantity(temp);
                        theManagerDB.setProductQuantity(order_id, item.getProduct().getId(), temp);
                        model.setValueAt(textField.getText(), row, 1);
                        t.revalidate();
                        t.repaint();
                    }
                }
                for (OrderMenus menu : orderMenus) {
                    if (menu.getName() == String.valueOf(t.getValueAt(row, 0))) {
                        menu.setQuantity(temp);
                        theManagerDB.setMenuQuantity(order_id, menu.getMenu().getId(), temp);
                        model.setValueAt(textField.getText(), row, 1);
                        t.revalidate();
                        t.repaint();
                    }
                }
                frame.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    /**
     * Method to delete a row on the table
     * 
     * @param row integer representing the row we want to delete
     */
    private void deleteRow(int row) {
        int table_id = theManagerDB.getTableID(order_id);
        for (OrderItems item : orderItems) {
            if (item.getName() == String.valueOf(t.getValueAt(row, 0))) {
                try {
                    theManagerDB.makeTableEmpty(table_id);
                    theManagerDB.deleteOrderItem(order_id, item.getProduct().getId());
                    theManagerDB.makeTableOccupied(order_id, table_id);
                    model.removeRow(row);
                    t.revalidate();
                    t.repaint();
                    return;
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }
        }
        for (OrderMenus menu : orderMenus) {
            if (menu.getName() == String.valueOf(t.getValueAt(row, 0))) {
                try {
                    theManagerDB.makeTableEmpty(table_id);
                    theManagerDB.deleteOrderMenu(order_id, menu.getMenu().getId());
                    theManagerDB.makeTableOccupied(order_id, table_id);
                    model.removeRow(row);
                    t.revalidate();
                    t.repaint();
                    return;
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }
        }
    }

    /**
     * Method to update table when we add to the order (included when the product was already in the table)
     * 
     * @param id integer representing the id
     * @param name string representing the name
     * @param price float representing the price
     * @param isProduct boolean to see if it's a product or menu
     */
    public void updateTable (int id, String name, float price, Boolean isProduct) {
        // Checks if the product already existed in the table
        // If it did we just have to change the quantity to one more product
        // After checking (wether it existed or not) we call the method modifyArrays
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(name)) {
                int temp = Integer.parseInt((String) model.getValueAt(i, 1))+1;
                model.setValueAt(Integer.toString(temp), i, 1);
                modifyArrays(id, name, price, temp, false, isProduct);
                return;
            }
        }
        // If it didn't exist we have to add another row with the product and quantity 1
        model.addRow(new String[] {name, Integer.toString(1)});
        modifyArrays(id, name, price, 1, true, isProduct);
        t.revalidate();
        t.repaint();
    }

    /**
     * Method that modifies the arrays when we add something to the order
     * 
     * @param id integer representing the id
     * @param name string representing the name
     * @param price float representing the price
     * @param quantity integer representing the quantity of this priduct in the order
     * @param isNew boolean representing if the product was new in the order or not
     * @param isProduct boolean to see if it's a product or menu
     */
    private void modifyArrays(int id, String name, float price, int quantity, Boolean isNew, Boolean isProduct) {
        // If the product was already in the order, we only have to set a new quantity
        if (!isNew) {
            for (OrderItems item : orderItems) {
                if (name == item.getName() && !isNew) {
                    item.setQuantity(quantity);
                    return;
                }
            }
            for (OrderMenus menu : orderMenus) {
                if (name == menu.getName() && !isNew) {
                    menu.setQuantity(quantity);
                    return;
                }
            }
        } 
        // If the product is new in the order, we have to create a new OrderItems/OrderMenus and add it to the corresponding array
        // (notice we create a new Product/Menu too)
        else if (isNew) {
            if (isProduct) {
                orderItems.add(new OrderItems(order_id, new Product(id, name, price, true), 1));
            } else if (!isProduct) {
                orderMenus.add(new OrderMenus(order_id, new Menu(id, name, price, true), 1));
            }
        }
    }
}
