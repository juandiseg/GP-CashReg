package windows.ordersWindow;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import iLayouts.FlowLayoutApplyer;
import objects.OrderItems;
import objects.OrderMenus;
import util.AbstractTable;
import util.AbstractUpdater;
import util.NumberInput;
import windows.MainWindow;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.util.ArrayList;

import java.text.DecimalFormat;

public class CashWindow extends AbstractUpdater {

    private int order_id;
    private float change;
    private JLabel labelGiven = new JLabel("Given: ");
    private JLabel labelChange = new JLabel("Change: ");
    private JTextField textField = new JTextField(20);
    private JButton okButton = new JButton("Ok");
    private JButton doneButton = new JButton("Done");
    private JButton cancelButton = new JButton("Cancel");
    private JPanel panel3 = new JPanel();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public CashWindow(AbstractUpdater previousWindow, int order_id) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.order_id = order_id;
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Cash Payment");

        ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(order_id);
        ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(order_id);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Order ID: " + order_id);

        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        new AbstractTable(panel, order_id, orderItems, orderMenus);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea("SUBTOTAL: " + df.format(getSubtotal()) + "€ \n" +
                                            "TAX: " + df.format(getTax()) + "€ \n" +
                                            "TOTAL: " + df.format(getTotal()) + "€ \n");
        textArea.setEditable(false);
        panel2.add(textArea, BorderLayout.NORTH);
        panel2.add(doneButton, BorderLayout.CENTER);
        panel2.add(cancelButton, BorderLayout.SOUTH);
        panel.add(panel2, BorderLayout.SOUTH);
        theFrame.add(panel);
        
        panel3.setPreferredSize(new Dimension(300, 450));
        panel3.add(labelGiven);
        panel3.add(textField);
        panel3.add(okButton);
        panel3.add(labelChange);
        panel3.add(keypad());
        theFrame.add(panel3);
    }

    private float getSubtotal() {
        ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(order_id);
        ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(order_id);
        float total = 0;
        for (OrderItems items : orderItems) {
            total = total + (items.getProduct().getPrice() * items.getQuantity());
        }
        for (OrderMenus menus : orderMenus) {
            total = total + (menus.getMenu().getPrice() * menus.getQuantity());
        }
        total = Float.parseFloat(df.format(total));
        return total;
    }

    private float getTax() {
        float tax = getSubtotal() / 10;
        tax = Float.parseFloat(df.format(tax));
        return tax;
    }

    private float getTotal() {
        return getSubtotal() + getTax();
    }
    
    private JPanel keypad() {
        JPanel p = new JPanel();
        new NumberInput(p, textField);
        return p;
    }

    @Override
    public void addActionListeners() {
        AbstractUpdater temp = this;
        int table_id = theManagerDB.getTableID(order_id);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!textField.getText().isEmpty() && Float.parseFloat(textField.getText()) >= getTotal()) {
                    float temp = Float.parseFloat(textField.getText());
                    change = temp - (getSubtotal() + getTax());
                    labelChange.setText("Change: " + df.format(change) + "€");
                    panel3.revalidate();
                    panel3.repaint();
                } else {
                    labelChange.setText("Wrong input");
                    panel3.revalidate();
                    panel3.repaint();
                }
            }
        });
        
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (labelChange.getText() != "Change: " && labelChange.getText() != "Wrong input") {

                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(theFrame, "Do you want a receipt?", null,
			            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                    
                    if (n == 0) {
                        theManagerDB.checkOutOrder(order_id, getTotal(), getTax(), getSubtotal(), true);
                        if (table_id != -1) theManagerDB.makeTableEmpty(table_id);
                        ReceiptWindow tempWind = new ReceiptWindow(temp, order_id, getSubtotal(), getTax(), getTotal());
                        tempWind.updateToThisMenu();
                        panel3.removeAll();
                    } else if (n == 1) {
                        theManagerDB.checkOutOrder(order_id, getTotal(), getTax(), getSubtotal(), true);
                        if (table_id != -1) theManagerDB.makeTableEmpty(table_id);
                        new MainWindow();
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }
}