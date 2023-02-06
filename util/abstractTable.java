package util;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import componentsFood.orderItems;

public class abstractTable extends JFrame {
    
    public abstractTable(JPanel panel, int order_id, ArrayList<orderItems> order) {
        
        DefaultTableModel model = new DefaultTableModel(new String[] { "Product", "Quantity" }, 0);
        
        for (orderItems product : order) {
            model.addRow(new String[] {product.getName(), Integer.toString(product.getQuantity())});
        }

        JTable t = new JTable(model);
        t.setBounds(30, 40, 200, 550);

        JScrollPane sp = new JScrollPane(t);

        panel.add(sp);
        panel.setVisible(true);
    }
}
