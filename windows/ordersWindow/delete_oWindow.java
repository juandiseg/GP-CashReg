package windows.ordersWindow;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import iLayouts.GridLayoutApplyer;
import util.abstractUpdater;
import windows.main_Window;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class delete_oWindow extends abstractUpdater {

    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("Back");
    private ArrayList<Integer> orders = new ArrayList<>();

    public delete_oWindow(abstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, theManagerDB.getOrders().size()+1));
        orders = theManagerDB.getOrders();
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Pick Order");
        // Adds a button for every table
        for (int i = 0; i < orders.size(); i++) {
            JButton button = new JButton("Order " + (orders.get(i)));
            theFrame.add(button);
            buttons.add(button);
        }
        theFrame.add(backButton);
    }

    @Override
    public void addActionListeners() {
        
        for (int i=0; i < orders.size(); i++) {
            int order_id = orders.get(i);
            int table_id = theManagerDB.getTableID(order_id);

            buttons.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(theFrame, "Are you sure you want to delete order " + order_id + "?", null,
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

                    if (n==0) {
                        if (table_id != -1) theManagerDB.makeTableEmpty(table_id);
                        theManagerDB.deleteAllOrderItems(order_id);
                        theManagerDB.deleteOrderSummary(order_id);
                        new main_Window();
                    }
                }
            });
        }

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }
}
