package windows.ordersWindow;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import iLayouts.GridLayoutApplyer;
import util.abstractUpdater;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class edit_oWindow extends abstractUpdater {

    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("Back");
    private ArrayList<Integer> orders = new ArrayList<>();

    public edit_oWindow(abstractUpdater previousWindow) {
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
        abstractUpdater temp = this;
        
        for (int i=0; i < orders.size(); i++) {
            int order_id = orders.get(i);
            int table_id = theManagerDB.getTableID(order_id);

            buttons.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    add_oWindow tempWind = new add_oWindow(temp, order_id, table_id, false);
                    buttons.removeAll(buttons);
                    tempWind.updateToThisMenu();
                }
            });
        }

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttons.removeAll(buttons);
                updateToPreviousMenu();
            }
        });
    }
}
