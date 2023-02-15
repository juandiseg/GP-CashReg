package windows.ordersWindow;

import iLayouts.FlowLayoutApplyer;
import objects.orderItems;
import util.abstractTable;
import util.abstractUpdater;
import windows.main_Window;
import windows.menuPanel.main_mWindow;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.BorderLayout;

public class add_oWindow extends abstractUpdater {

    private abstractUpdater previousWindow;
    private int order_id;
    private int table_id;
    private JButton checkButton = new JButton("Check-out");
    private JButton backButton = new JButton("Back");
    private JButton doneButton = new JButton("Done");
    private Boolean newOrder;
    
    public add_oWindow(abstractUpdater previousWindow, int order_id, int table_id, Boolean newOrder) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.previousWindow = previousWindow;
        this.order_id = order_id;
        this.table_id = table_id;
        this.newOrder = newOrder;
    }

    @Override
    public void addComponents() {
        theFrame.setTitle(null);
        ArrayList<orderItems> order = theManagerDB.getOrderItems(order_id);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Order ID: " + order_id);

        panel.setLayout(new BorderLayout());

        panel.add(label, BorderLayout.NORTH);
        
        JPanel panel2 = new JPanel();
        abstractTable table = new abstractTable(panel2, order_id, order);
        table.clickableTable();
        panel.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.add(checkButton, BorderLayout.NORTH);
        if (newOrder) panel3.add(doneButton, BorderLayout.SOUTH);
        else panel3.add(backButton, BorderLayout.SOUTH);
        panel.add(panel3, BorderLayout.PAGE_END);

        JPanel panel4 = new JPanel();
        new main_mWindow(previousWindow, panel4, order_id, table_id, newOrder);

        theFrame.add(panel);
        theFrame.add(panel4);
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check_oWindow tempWind = new check_oWindow(temp, order_id);
                tempWind.updateToThisMenu();
            }
        });

        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new main_Window();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }
}
