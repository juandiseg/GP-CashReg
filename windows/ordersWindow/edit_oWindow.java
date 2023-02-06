package windows.ordersWindow;

import componentsFood.orderItems;
import iLayouts.FlowLayoutApplyer;
import util.abstractTable;
import util.abstractUpdater;
import windows.main_Window;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.BorderLayout;

public class edit_oWindow extends abstractUpdater {

    private int order_id;
    private JButton cancelButton = new JButton("Cancel");
    
    public edit_oWindow(abstractUpdater previousWindow, int order_id) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.order_id = order_id;
    }

    @Override
    public void addComponents() {
        ArrayList<orderItems> order = theManagerDB.getOrderItems(order_id);
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        new abstractTable(panel, order_id, order);

        panel.add(cancelButton, BorderLayout.PAGE_END);
        theFrame.add(panel);
        
    }

    @Override
    public void addActionListeners() {
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new main_Window();
            }
        });
    }
}
