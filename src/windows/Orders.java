
package windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import objects.OrderItems;
import objects.OrderMenus;
import util.ManagerDB;
import util.NumberInput;
import util.TableInput;

// Class to see panel with the orders
public class Orders implements ActionListener {
    
    private ManagerDB theManagerDB = new ManagerDB();
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<Integer> orders = new ArrayList<>();
    private JPanel panel2;
    private JPanel panel1;
    private JPanel panel4;
    private NumberInput numberInput;
    private String name;

    /**
     * Constructor for orders
     * 
     * @param panel2 panel where the orders will appear
     * @param panel1 panel where the order's information will appear
     * @param panel4 panel with the options to order will appear
     * @param numberInput keypad that will be used when you want to change the quantity of an item in the order
     * @param name why you are calling the class Orders
     */
    public Orders(JPanel panel2, JPanel panel1, JPanel panel4, NumberInput numberInput, String name) {
        this.panel2 = panel2;
        this.panel1 = panel1;
        this.panel4 = panel4;
        this.numberInput = numberInput;
        this.name = name;
        orders = theManagerDB.getOrders();
        
        panel2.setPreferredSize(panel2.getPreferredSize());
        panel2.setBorder(null);
        if (orders.size() <= 2) panel2.setLayout(new GridLayout(1, orders.size()));
        else panel2.setLayout(new GridLayout(2, orders.size()/2));
        
        for (int i = 0; i < orders.size(); i++) {
            JButton button = new JButton("Order " + (orders.get(i)));
            panel2.add(button);
            buttons.add(button);
            button.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if you want to delete an order
        if (name == "Delete") {
            for (int i = 0; i < orders.size(); i++) {
                int order_id = orders.get(i);
                int table_id = theManagerDB.getTableID(order_id);
                
                for (JButton button : buttons) {
                    if ((e.getSource().equals(button)) && (button.getText().equals("Order " + orders.get(i)))) {
                        Object[] options = {"Yes", "No"};
                        int n = JOptionPane.showOptionDialog(panel2, "Are you sure you want to delete order " + order_id + "?", null,
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
    
                        if (n==0) {
                            if (table_id != -1) theManagerDB.makeTableEmpty(table_id);
                            theManagerDB.deleteAllOrderItems(order_id);
                            theManagerDB.deleteAllOrderMenus(order_id);
                            theManagerDB.deleteOrderSummary(order_id);
                            
                            panel2.removeAll();
                            new Orders(panel2, panel1, panel4, numberInput, "Delete");
                            panel2.revalidate();
                            panel2.repaint();
                        }
                    }
                }
            }
        } 
        // if you don't want to delete an order then it will show the information of the order and you can edit it
        else {
            for (int i = 0; i < orders.size(); i++) {
                int order_id = orders.get(i);
                int table_id = theManagerDB.getTableID(order_id);
                
                for (JButton button : buttons) {
                    if ((e.getSource().equals(button)) && (button.getText().equals("Order " + order_id))) {
                        ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(order_id);
                        ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(order_id);
                        
                        panel1.removeAll();
                        TableInput t = new TableInput(panel1, order_id, orderItems, orderMenus);
                        t.clickableTable();
                        panel1.revalidate();
                        panel1.repaint();

                        // if what you clicked was "Payment" it will direct you to the Payment layout
                        if (name == "Payment") {
                            panel1.removeAll();
                            new Payment(panel1, panel2, panel4, numberInput, order_id);
                            panel1.revalidate();
                            panel1.repaint();
                        } else {
                            panel4.removeAll();
                            new Options(t, null, panel4, order_id, table_id);
                            panel4.revalidate();
                            panel4.repaint();
                        }
                    }
                }
            }
        }
    }
}