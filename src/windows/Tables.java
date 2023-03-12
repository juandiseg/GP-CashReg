
package windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import objects.OrderItems;
import objects.OrderMenus;
import objects.Table;
import util.ManagerDB;
import util.TableInput;

public class Tables implements ActionListener {
    
    private ManagerDB theManagerDB = new ManagerDB();
    private ArrayList<Table> tables = theManagerDB.getTables();
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<Integer> occupiedTables = theManagerDB.getOccupiedTables();
    private JButton takeAway = new JButton("Take Away");
    private JPanel panel1;
    private JPanel panel4;
    private String name;

    public Tables(JPanel panel2, JPanel panel1, JPanel panel4, String name) {
        this.panel1 = panel1;
        this.panel4 = panel4;
        this.name = name;
        
        panel2.setBorder(null);
        panel2.setPreferredSize(panel2.getPreferredSize());
        if (tables.size()+1 < 9) panel2.setLayout(new GridLayout(2, tables.size()/2));
        else panel2.setLayout(new GridLayout(3, tables.size()/3));
        
        for (Table table : tables) {
            JButton button = new JButton("Table " + table.getID());
            panel2.add(button);
            buttons.add(button);
            button.addActionListener(this);
        }
        
        if (name == "Add") {
            panel2.add(takeAway);
            takeAway.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : buttons) {
            for (Table table : tables) {
                int table_id = table.getID();
                if ((e.getSource().equals(button)) && (button.getText().equals("Table " + table_id))) {
                    ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(table.getOrder());
                    ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(table.getOrder());
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
                    } else if (name == "Tables") {
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
    
    public void freeTables() {
        for (JButton button : buttons) {
            for (Integer table : occupiedTables) {
                if (button.getText().equals("Table " + table)) {
                    button.setEnabled(false);
                }
            }
        }
    }

    public void updateTablesPanel(int table_id) {
        for (Table table : tables) {
            if (table.getID() == table_id) {
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
