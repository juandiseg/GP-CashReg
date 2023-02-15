package windows.ordersWindow;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import iLayouts.FlowLayoutApplyer;
import objects.orderItems;
import util.abstractUpdater;
import windows.main_Window;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;

public class receipt_oWindow extends abstractUpdater {

    private int order_id;
    private float subtotal;
    private float tax;
    private float total;
    private JTable t = new JTable() {
        public boolean isCellEditable(int row, int column){  
            return false;  
        }
    };
    private DefaultTableModel model = new DefaultTableModel(new String[] { "Quantity", "Product", "Price" }, 0);
    private JButton button1 = new JButton("Print Receipt");
    private JButton button2 = new JButton("Email Receipt");
    private JButton doneButton = new JButton("Done");
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public receipt_oWindow(abstractUpdater previousWindow, int order_id, float subtotal, float tax, float total) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.order_id = order_id;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
    }

    private void createTable(JPanel panel) {
        ArrayList<orderItems> order = theManagerDB.getOrderItems(order_id);
        
        for (orderItems product : order) {
            model.addRow(new String[] {Integer.toString(product.getQuantity()), product.getName(), df.format(product.getProduct().getPrice()*product.getQuantity())});
        }

        t.setModel(model);
        t.setRowSelectionAllowed(false);
        t.setBounds(30, 40, 200, 550);

        JScrollPane sp = new JScrollPane(t);

        panel.add(sp);
        panel.setVisible(true);
    }

    @Override
    public void addComponents() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Order ID: " + order_id);
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        createTable(panel);
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea("\nsSUBTOTAL: " + df.format(subtotal) + "€ \n" +
                                            "TAX: " + df.format(tax) + "€ \n" +
                                            "TOTAL: " + df.format(total) + "€ \n\n\n" +
                                            "THANK YOU FOR DINING WITH US!\nPLEASE COME AGAIN", 10, 30);
        textArea.setEditable(false);

        JPanel panel3 = new JPanel();
        panel3.add(button1);
        panel3.add(button2);
        panel3.add(doneButton);

        panel2.add(textArea, BorderLayout.NORTH);
        panel2.add(panel3, BorderLayout.SOUTH);

        theFrame.add(panel);
        theFrame.add(panel2);
    }

    @Override
    public void addActionListeners() {
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new main_Window();
            }
        });
    }
}
