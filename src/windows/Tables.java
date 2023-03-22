
package windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import objects.OrderItems;
import objects.OrderMenus;
import util.ManagerDB;
import util.TableInput;

// Class to see the tables and their information
public class Tables implements ActionListener {
    
    private ManagerDB theManagerDB = new ManagerDB();
    private ArrayList<Integer> tablesID = theManagerDB.getTablesIDs();
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<Integer> occupiedTables = theManagerDB.getOccupiedTables();
    private JButton takeAway = new JButton("Take Away");
    private JPanel panel1;
    private JPanel panel4;
    private String name;

    /**
     * Constructor for Tables
     * 
     * @param panel2 panel where the buttons for the tables go
     * @param panel1 panel where the information for the tables go
     * @param panel4 panel for the option when you want to order are
     * @param name what you want to do with the tables
     */
    public Tables(JPanel panel2, JPanel panel1, JPanel panel4, String name) {
        this.panel1 = panel1;
        this.panel4 = panel4;
        this.name = name;
        
        panel2.setBorder(null);
        panel2.setPreferredSize(panel2.getPreferredSize());
        if (tablesID.size()+1 < 9) panel2.setLayout(new GridLayout(2, tablesID.size()/2));
        else panel2.setLayout(new GridLayout(3, tablesID.size()/3));
        
        for (Integer table_id : tablesID) {
            JButton button = new JButton("Table " + table_id);
            panel2.add(button);
            buttons.add(button);
            button.addActionListener(this);
        }
        
        // this button only shows when you want to add a new order
        if (name == "Add") {
            panel2.add(takeAway);
            takeAway.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : buttons) {
            for (Integer table_id : tablesID) {
                if ((e.getSource().equals(button)) && (button.getText().equals("Table " + table_id))) {
                    ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(theManagerDB.getOrderID(table_id));
                    ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(theManagerDB.getOrderID(table_id));
                    // if you want to add an order
                    if (name == "Add") {
                        int order_id = theManagerDB.getLastOrderID()+1;
                        panel1.removeAll();
                        TableInput t = new TableInput(panel1, order_id, orderItems, orderMenus);
                        t.clickableTable();
                        panel1.revalidate();
                        panel1.repaint();
                        
                        panel4.removeAll();
                        new Options(t, this, panel4, order_id, table_id);
                        panel4.revalidate();
                        panel4.repaint();
                    } 
                    // if you are looking at the tables (you should be able to edit an order from there)
                    else if (name == "Tables") {
                        int order_id = theManagerDB.getOrderID(table_id);
                        panel1.removeAll();
                        TableInput t = new TableInput(panel1, order_id, orderItems, orderMenus);
                        t.clickableTable();
                        panel1.revalidate();
                        panel1.repaint();
                        
                        panel4.removeAll();
                        new Options(t, this, panel4, order_id, table_id);
                        panel4.revalidate();
                        panel4.repaint();
                    }
                    // if it's the default (used when you start to run the program and when you have finished paying)
                    else if (name == "Default") {
                        int order_id = theManagerDB.getOrderID(table_id);
                        panel1.removeAll();
                        new TableInput(panel1, order_id, orderItems, orderMenus);
                        panel1.revalidate();
                        panel1.repaint();
                    }
                }
            }
        }

        if (e.getSource().equals(takeAway)) {
            int order_id = theManagerDB.getLastOrderID()+1;
            ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(order_id);
            ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(order_id);
            panel1.removeAll();
            TableInput t = new TableInput(panel1, order_id, orderItems, orderMenus);
            t.clickableTable();
            panel1.revalidate();
            panel1.repaint();
            
            panel4.removeAll();
            new Options(t, this, panel4, order_id, -1);
            panel4.revalidate();
            panel4.repaint();
        }
    }
    
    /**
     * To only show the free tables, if a table is occupied it will disable the button
     */
    public void freeTables() {
        for (JButton button : buttons) {
            for (Integer table : occupiedTables) {
                if (button.getText().equals("Table " + table)) {
                    button.setEnabled(false);
                }
            }
        }
    }

    /**
     * It updates the panel when the availability of a table changes (when you are making a new order)
     * 
     * @param table_id the table ID you want to change the availability of
     */
    public void updateTablesPanel(int table_id) {
        for (Integer table : tablesID) {
            if (table == table_id) {
                occupiedTables.add(table_id);
            }
        }

        for (JButton button : buttons) {
            for (Integer table : occupiedTables) {
                if (button.getText().equals("Table " + table)) {
                    button.setEnabled(false);
                }
            }
        }
    }
}
