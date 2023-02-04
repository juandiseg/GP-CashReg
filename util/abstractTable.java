package util;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import componentsFood.orderItems;
import iLayouts.FlowLayoutApplyer;
import iLayouts.iLayout;
import iLayouts.placeholderLayoutApplyer;
import windows.main_Window;

public class abstractTable extends abstractUpdater {
    
    private JButton checkoutButton = new JButton("Check out");
    private JButton cancelButton = new JButton("Cancel");
    protected DefaultTableModel model;
    protected JTable myTable;
    private int order_id;
    
    public abstractTable(abstractUpdater previousWindow, int order_id) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.order_id = order_id;
    }

    @Override
    public void addComponents() {
        JPanel p = new JPanel();
        p.add(createTable());
        p.add(checkoutButton);
        p.add(cancelButton);  
        theFrame.add(p);      
    }

    @Override
    public void addActionListeners() {
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new main_Window();
            }
        });
    }

    private JPanel createTable() {
        JPanel p2 = new JPanel();
        try {
            DefaultTableModel model = new DefaultTableModel(new String[] { "Product", "Quantity" }, 0);
            ArrayList<orderItems> order = theManagerDB.getOrderItems(order_id);
            for (orderItems product : order) {
                model.addRow(new String[] {product.getName(), Integer.toString(product.getQuantity())});
            }
            JTable t = new JTable(model);
            t.setBounds(30, 40, 200, 300);
            JScrollPane sp = new JScrollPane(t);
            p2.add(cancelButton);
            p2.add(sp);
        } catch (Exception e) {
            System.out.println(e);
        }
        return p2;
    }
}
