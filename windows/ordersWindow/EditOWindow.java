package windows.ordersWindow;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import iLayouts.GridLayoutApplyer;
import util.AbstractUpdater;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class EditOWindow extends AbstractUpdater {

    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("Back");
    private ArrayList<Integer> orders = new ArrayList<>();

    public EditOWindow(AbstractUpdater previousWindow) {
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
        AbstractUpdater temp = this;
        
        for (int i=0; i < orders.size(); i++) {
            int order_id = orders.get(i);
            int table_id = theManagerDB.getTableID(order_id);

            buttons.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    AddOWindow tempWind = new AddOWindow(temp, order_id, table_id, false);
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
