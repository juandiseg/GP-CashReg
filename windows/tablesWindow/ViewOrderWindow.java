package windows.tablesWindow;

import iLayouts.FlowLayoutApplyer;
import objects.OrderItems;
import objects.OrderMenus;
import util.AbstractTable;
import util.AbstractUpdater;
import windows.MainWindow;
import windows.ordersWindow.AddOWindow;
import windows.ordersWindow.CheckOWindow;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class ViewOrderWindow extends AbstractUpdater {

    private int order_id;
    private int table_id;
    private JButton button1 = new JButton("Edit Order");
    private JButton button2 = new JButton("Delete Order");
    private JButton button3 = new JButton("Check-out Order");
    private JButton backButton = new JButton("Back");
    
    public ViewOrderWindow(AbstractUpdater previousWindow, int order_id, int table_id) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.order_id = order_id;
        this.table_id = table_id;
    }

    @Override
    public void addComponents() {
        ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(order_id);
        ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(order_id);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Order ID: " + order_id);
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        new AbstractTable(panel, order_id, orderItems, orderMenus);
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
        AbstractUpdater temp = this;
        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddOWindow tempWind = new AddOWindow(temp, order_id, table_id, false);
                tempWind.updateToThisMenu();
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Yes", "No"};
                int n = JOptionPane.showOptionDialog(theFrame, "Are you sure you want to delete order " + order_id + "?", null,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

                if (n==0) {
                    theManagerDB.makeTableEmpty(table_id);
                    theManagerDB.deleteAllOrderItems(order_id);
                    theManagerDB.deleteOrderSummary(order_id);
                    new MainWindow();
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CheckOWindow tempWind = new CheckOWindow(temp, order_id);
                tempWind.updateToThisMenu();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
        
    }
}
