package windows.ordersWindow;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import iLayouts.GridLayoutApplyer;
import util.abstractUpdater;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class assist_check_oWindow extends abstractUpdater {

    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("Back");
    private ArrayList<Integer> orders = new ArrayList<>();

    public assist_check_oWindow(abstractUpdater previousWindow) {
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
            buttons.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    check_oWindow tempWind = new check_oWindow(temp, order_id);
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
