package windows.ordersWindow;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import iLayouts.GridLayoutApplyer;
import util.abstractUpdater;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class assist_edit_oWindow extends abstractUpdater {

    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("Back");
    private static ArrayList<Integer> orders = theManagerDB.getOrders();

    public assist_edit_oWindow(abstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, orders.size()+1));
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
            int table_id = orders.get(i);
            buttons.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    edit_oWindow tempWind = new edit_oWindow(temp, table_id);
                    tempWind.updateToThisMenu();
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
