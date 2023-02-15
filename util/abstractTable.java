package util;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import objects.orderItems;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class abstractTable {

    private JTable t = new JTable() {
        public boolean isCellEditable(int row, int column){  
            return false;  
        }
    };
    private DefaultTableModel model = new DefaultTableModel(new String[] { "Product", "Quantity" }, 0);
    private int order_id;
    private ArrayList<orderItems> order;
    private managerDB theManagerDB = new managerDB();
    
    public abstractTable(JPanel panel, int order_id, ArrayList<orderItems> order) {
        this.order_id = order_id;
        this.order = order;

        for (orderItems product : order) {
            model.addRow(new String[] {product.getName(), Integer.toString(product.getQuantity())});
        }

        t.setModel(model);
        t.setRowSelectionAllowed(false);
        t.setBounds(30, 40, 200, 550);

        JScrollPane sp = new JScrollPane(t);

        panel.add(sp);
        panel.setVisible(true);
    }

    public void clickableTable() {
        t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int table_id = theManagerDB.getTableID(order_id);
                int row = t.getSelectedRow();
                Object[] options = {"Edit Quantity", "Delete Item"};
                int n = JOptionPane.showOptionDialog(null, "What do you want to do?", null,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, t);

                if (n == 0) changeQuantity(row);
                else if (n == 1) {
                    theManagerDB.deleteOrderItem(order_id, row);
                    for (orderItems product : order) {
                        if (product.getName() == String.valueOf(t.getValueAt(row, 0))) {
                            try {
                                theManagerDB.makeTableEmpty(table_id);
                                theManagerDB.deleteOrderItem(order_id, product.getProduct().getId());
                                theManagerDB.makeTableOccupied(order_id, table_id);
                                model.removeRow(row);
                                t.revalidate();
                                t.repaint();
                            } catch (Exception exception) {
                                System.out.println(exception);
                            }
                        }
                    }
                }
            }
        });
    }

    private void changeQuantity(int row) {
        // String value = t.getModel().getValueAt(row, col).toString();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(210, 150);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
         
        JPanel panel1 = new JPanel();
        JButton button1 = new JButton("-");
        JTextField textField = new JTextField(String.valueOf(t.getValueAt(row, 1)), 5);
        textField.setHorizontalAlignment(JTextField.CENTER);
        JButton button2 = new JButton("+");
        panel1.add(button1);
        panel1.add(textField);
        panel1.add(button2);

        JPanel panel2 = new JPanel();
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");
        panel2.add(okButton);
        panel2.add(cancelButton);

        JLabel label = new JLabel("Specify quantity");
        label.setHorizontalAlignment(JTextField.CENTER);
         
        frame.add(label, BorderLayout.NORTH);
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.SOUTH);
        
        frame.setVisible(true);

        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int temp = Integer.parseInt(textField.getText()) - 1;
                if (temp > 0) {
                    textField.setText(Integer.toString(temp));
                    panel1.revalidate();
                    panel1.repaint();
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int temp = Integer.parseInt(textField.getText()) + 1;
                textField.setText(Integer.toString(temp));
                panel1.revalidate();
                panel1.repaint();
            }
        });
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int temp = Integer.parseInt(textField.getText());
                for (orderItems product : order) {
                    if (product.getName() == String.valueOf(t.getValueAt(row, 0))) {
                        product.setQuantity(temp);
                        theManagerDB.setProductQuantity(order_id, product.getProduct().getId(), temp);
                        model.setValueAt(textField.getText(), row, 1);
                        t.revalidate();
                        t.repaint();
                    }
                }
                frame.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public void addRow (String name) {
        model.addRow(new String[] {name, Integer.toString(1)});
        t.revalidate();
        t.repaint();
    }
}
