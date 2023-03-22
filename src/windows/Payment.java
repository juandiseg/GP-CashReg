package windows;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.BorderFactory;

import objects.OrderItems;
import objects.OrderMenus;
import util.ManagerDB;
import util.NumberInput;
import util.Receipt;
import util.TableInput;

// Class to pay an order
// whenever you pay, a pdf with the receipt will be created and added to the folder receipts
public class Payment implements ActionListener {

    private ManagerDB theManagerDB = new ManagerDB();
    private int order_id;
    private JTable t = new JTable() {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel model = new DefaultTableModel(new String[] { "Product", "Quantity", "Price", "Value" },
            0);
    private JButton button1 = new JButton("Card");
    private JButton button2 = new JButton("Cash");
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private JPanel thePanel;
    private JPanel panel2;
    private JPanel panel4;
    private NumberInput numberInput;
    private JLabel labelGiven = new JLabel("Given: ");
    private JLabel labelChange = new JLabel("");
    private JTextField textField = new JTextField(15);
    private JButton okButton = new JButton("Ok");
    private JButton payButton = new JButton("Pay");
    private float change;

    /**
     * Constructor of Payment
     * 
     * @param panel1 panel where the order's information will be displayed
     * @param panel2 panel where if you are paying with cash the change functionality will be displayed
     * @param panel4 panel where the payment information is and where you will be able to choose between card or cash
     * @param numberInput keypad to enter the change if you are paying with cash
     * @param order_id order ID of the order you want to pay
     */
    public Payment(JPanel panel1, JPanel panel2, JPanel panel4, NumberInput numberInput, int order_id) {
        this.thePanel = panel1;
        this.panel2 = panel2;
        this.panel4 = panel4;
        this.numberInput = numberInput;
        this.order_id = order_id;

        thePanel.setBorder(null);
        thePanel.setSize(thePanel.getPreferredSize());
        thePanel.setLayout(new BorderLayout());

        createTable();
        createInvoice();
    }

    /**
     * Creates the table with all the order's information
     */
    private void createTable() {
        ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(order_id);
        ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(order_id);
        JPanel labelPanel = new JPanel();
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout());
        JLabel label = new JLabel("Order: " + order_id);
        TableColumnModel columnModel = t.getColumnModel(); 

        for (OrderItems item : orderItems) {
            model.addRow(new String[] { item.getName(), Integer.toString(item.getQuantity()),
                    df.format(item.getProduct().getPrice()) + "€",
                    df.format(item.getProduct().getPrice() * item.getQuantity()) + "€" });
        }
        for (OrderMenus menu : orderMenus) {
            model.addRow(new String[] { menu.getName(), Integer.toString(menu.getQuantity()),
                    df.format(menu.getMenu().getPrice()) + "€",
                    df.format(menu.getMenu().getPrice() * menu.getQuantity()) + "€" });
        }

        t.setModel(model);
        t.setRowSelectionAllowed(false);
        columnModel.getColumn(0).setPreferredWidth(190);
        columnModel.getColumn(1).setPreferredWidth(50);

        JScrollPane sp = new JScrollPane(t);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        labelPanel.add(label);
        tablePanel.add(sp);

        thePanel.add(labelPanel, BorderLayout.NORTH);
        thePanel.add(tablePanel, BorderLayout.AFTER_LINE_ENDS);
    }

    /**
     * Creates a little text area with the information of the payment (subtotal, tax and total)
     */
    private void createInvoice() {
        JTextArea textArea = new JTextArea("\n\n     SUBTOTAL: " + df.format(getSubtotal()) + "€ \n" +
                "     TAX: " + df.format(getTax()) + "€ \n" +
                "     TOTAL: " + df.format(getTotal()) + "€", 8, 40);
        textArea.setEditable(false);
        JPanel textPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        panel4.setLayout(new FlowLayout());
        button1.setPreferredSize(new Dimension(90, 50));
        button2.setPreferredSize(new Dimension(90, 50));
        button1.addActionListener(this);
        button2.addActionListener(this);

        textPanel.add(textArea);
        buttonsPanel.add(button1);
        buttonsPanel.add(button2);

        panel4.removeAll();
        panel4.add(textPanel);
        panel4.add(buttonsPanel);
        panel4.revalidate();
        panel4.repaint();
    }

    /**
     * To get the subtotal of the order
     * @return a float with the subtotal
     */
    private float getSubtotal() {
        ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(order_id);
        ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(order_id);
        float subtotal = 0;
        for (OrderItems items : orderItems) {
            subtotal = subtotal + (items.getProduct().getPrice() * items.getQuantity());
        }
        for (OrderMenus menus : orderMenus) {
            subtotal = subtotal + (menus.getMenu().getPrice() * menus.getQuantity());
        }
        subtotal = Float.parseFloat(df.format(subtotal));
        return subtotal;
    }

    /**
     * To get the tax, a 10% of the subtotal
     * @return a float with the tax
     */
    private float getTax() {
        float tax = getSubtotal() / 10;
        tax = Float.parseFloat(df.format(tax));
        return tax;
    }

    /**
     * To get the total of the order, subtotal + tax
     * @return a float with the total
     */
    private float getTotal() {
        return getSubtotal() + getTax();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int table_id = theManagerDB.getTableID(order_id);
        // if you want to pay with card we are just simulating you are paying
        if (e.getSource().equals(button1)) {
            Object[] options = { "Ok", "Cancel" };
            int n = JOptionPane.showOptionDialog(null, "Insert Card", "Card Payment",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            if (n == 0) {
                theManagerDB.checkOutOrder(order_id, getTotal(), getTax(), getSubtotal(), false);
                if (table_id != -1)
                    theManagerDB.makeTableEmpty(table_id);
                new Receipt(order_id, table_id, df.format(getSubtotal()), df.format(getTax()), df.format(getTotal()),
                        df.format(getTotal()), "0.00");

                // we can't really print it or send it by email, so the buttons don't really work
                Object[] options2 = { "Print receipt", "Send by email", "Both" };
                int n2 = JOptionPane.showOptionDialog(null, "Payment successful", "Receipt",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, null);

                setDefaultSettings();
            }
        }

        // if you want to pay with cash
        if (e.getSource().equals(button2)) {
            panel2.setPreferredSize(panel2.getSize());
            panel2.setBorder(BorderFactory.createEmptyBorder(80, 80, 0, 80));
            panel2.setLayout(new FlowLayout());
            okButton.addActionListener(this);

            panel2.removeAll();
            panel2.add(labelGiven);
            panel2.add(textField);
            panel2.add(okButton);
            panel2.add(labelChange);
            panel2.revalidate();
            panel2.repaint();

            numberInput.setDisplay(textField);
        }

        // when paying with cash, to get the change
        if (e.getSource().equals(okButton)) {
            if (!textField.getText().isEmpty() && Float.parseFloat(textField.getText()) >= getTotal()) {
                float temp = Float.parseFloat(textField.getText());
                change = temp - (getSubtotal() + getTax());
                labelChange.setText("Change: " + df.format(change) + "€");
                payButton.addActionListener(this);
                panel2.remove(payButton);
                panel2.add(payButton);
                panel2.revalidate();
                panel2.repaint();
            } else {
                labelChange.setText("Wrong input");
                panel2.revalidate();
                panel2.repaint();
            }
        }

        // when paying with cash when you already have the change and you finally want to pay
        if (e.getSource().equals(payButton)) {
            theManagerDB.checkOutOrder(order_id, getTotal(), getTax(), getSubtotal(), true);
            if (table_id != -1)
                theManagerDB.makeTableEmpty(table_id);
            new Receipt(order_id, table_id, df.format(getSubtotal()), df.format(getTax()), df.format(getTotal()),
                    textField.getText(), df.format(change));
                    
            // we can't really print it or send it by email, so the buttons don't really work
            Object[] options2 = { "Print receipt", "Send by email", "Both" };
            int n2 = JOptionPane.showOptionDialog(null, "Payment successful", "Receipt",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, null);

            setDefaultSettings();
        }
    }

    /**
     * After you pay, it will take you to the "main" panel or "default" window
     */
    private void setDefaultSettings() {
        ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(-1);
        ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(-1);
        thePanel.removeAll();
        new TableInput(thePanel, -1, orderItems, orderMenus);
        thePanel.revalidate();
        thePanel.repaint();

        panel2.removeAll();
        new Tables(panel2, panel2, panel4, null);
        panel2.revalidate();
        panel2.repaint();

        panel4.removeAll();
        new Options(null, null, panel4, -1, -1);
        panel4.revalidate();
        panel4.repaint();
    }
}
