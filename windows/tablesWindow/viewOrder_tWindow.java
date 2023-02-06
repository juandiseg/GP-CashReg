package windows.tablesWindow;

import componentsFood.orderItems;
import iLayouts.FlowLayoutApplyer;
import util.abstractTable;
import util.abstractUpdater;
import windows.ordersWindow.edit_oWindow;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.GridLayout;

public class viewOrder_tWindow extends abstractUpdater {

    private int order_id;
    private JButton button1 = new JButton("Edit Order");
    private JButton button2 = new JButton("Delete Order");
    private JButton button3 = new JButton("Check-out Order");
    private JButton backButton = new JButton("Back");
    
    public viewOrder_tWindow(abstractUpdater previousWindow, int order_id) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.order_id = order_id;
    }

    @Override
    public void addComponents() {
        ArrayList<orderItems> order = theManagerDB.getOrderItems(order_id);
        JPanel panel = new JPanel();
        new abstractTable(panel, order_id, order);
        theFrame.add(panel);
        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(300, 550));
        panel2.setLayout(new GridLayout(4, 1));
        panel2.add(button1);
        panel2.add(button2);
        panel2.add(button3);
        panel2.add(backButton);
        theFrame.add(panel2);
        
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edit_oWindow tempWind = new edit_oWindow(temp, order_id);
                tempWind.updateToThisMenu();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // edit_oWindow tempWind = new edit_oWindow(temp, order_id);
                // tempWind.updateToThisMenu();
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // edit_oWindow tempWind = new edit_oWindow(temp, order_id);
                // tempWind.updateToThisMenu();
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
        
    }
}
