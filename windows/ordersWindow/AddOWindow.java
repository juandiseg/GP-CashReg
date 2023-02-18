package windows.ordersWindow;

import iLayouts.FlowLayoutApplyer;
import objects.OrderItems;
import objects.OrderMenus;
import util.AbstractTable;
import util.AbstractUpdater;
import windows.MainWindow;
import windows.menuPanel.MainMWindow;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.BorderLayout;

public class AddOWindow extends AbstractUpdater {

    private int order_id;
    private int table_id;
    private JButton checkButton = new JButton("Check-out");
    private JButton backButton = new JButton("Back");
    private JButton doneButton = new JButton("Done");
    private Boolean newOrder;
    private AbstractTable table;
    
    public AddOWindow(AbstractUpdater previousWindow, int order_id, int table_id, Boolean newOrder) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.order_id = order_id;
        this.table_id = table_id;
        this.newOrder = newOrder;
    }

    @Override
    public void addComponents() {
        theFrame.setTitle(null);
        ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(order_id);
        ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(order_id);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Order ID: " + order_id);

        panel.setLayout(new BorderLayout());

        panel.add(label, BorderLayout.NORTH);
        
        JPanel panel2 = new JPanel();
        table = new AbstractTable(panel2, order_id, orderItems, orderMenus);
        table.clickableTable();
        panel.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.add(checkButton, BorderLayout.NORTH);
        if (newOrder) panel3.add(doneButton, BorderLayout.SOUTH);
        else panel3.add(backButton, BorderLayout.SOUTH);
        panel.add(panel3, BorderLayout.PAGE_END);

        JPanel panel4 = new JPanel();
        new MainMWindow(table, panel4, order_id, table_id);

        theFrame.add(panel);
        theFrame.add(panel4);
    }

    @Override
    public void addActionListeners() {
        AbstractUpdater temp = this;
        
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CheckOWindow tempWind = new CheckOWindow(temp, order_id);
                tempWind.updateToThisMenu();
            }
        });

        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }
}
