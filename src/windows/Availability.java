package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import objects.Menu;
import objects.Product;
import util.ManagerDB;

public class Availability {
    
    private JTable t = new JTable() {
        public boolean isCellEditable(int row, int column){  
            return false;  
        }
    };
    private DefaultTableModel model = new DefaultTableModel(new String[] { "Product", "Availability" }, 0);
    private String button;
    private ArrayList<Product> products;
    private ArrayList<Menu> menus;
    private JButton button1 = new JButton("Products");
    private JButton button2 = new JButton("Menus");
    private ManagerDB theManagerDB = new ManagerDB();
    private JPanel thePanel;

    public Availability(JPanel panel1, JPanel panel4) {
        thePanel = panel1;
        this.products = theManagerDB.productAvailability();
        this.menus = theManagerDB.menuAvailability();
        
        thePanel.setBorder(null);
        thePanel.setPreferredSize(panel1.getSize());
        thePanel.setLayout(new BorderLayout());

        addActionListeners();

        createTable(null);

        panel4.removeAll();
        panel4.add(button1);
        panel4.add(button2);
        panel4.revalidate();
        panel4.repaint();
    }
    
    private void createTable(String button) {
        if (button == "Products") {
            for (Product product : products) {
                model.addRow(new String[] {product.getName(), Boolean.toString(product.getActive())});
            }
        } else if (button == "Menus") {
            for (Menu menu : menus) {
                model.addRow(new String[] {menu.getName(), Boolean.toString(menu.getActive())});
            }
        }

        t.setModel(model);
        t.setRowSelectionAllowed(false);

        JScrollPane sp = new JScrollPane(t);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        thePanel.removeAll();
        thePanel.add(sp);
        thePanel.revalidate();
        thePanel.repaint();
    }

    private void addActionListeners() {
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button = "Products";
                model.setRowCount(0);
                thePanel.removeAll();
                createTable(button);
                thePanel.revalidate();
                thePanel.repaint();
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button = "Menus";
                model.setRowCount(0);
                thePanel.removeAll();
                createTable(button);
                thePanel.revalidate();
                thePanel.repaint();
            }
        });

        t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = t.getSelectedRow();
                Object[] options = {"Yes", "No"};
                int n = JOptionPane.showOptionDialog(null, "Do you want to change the availability?", null,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, t);

                if (n == 0) {
                    if (button == "Products") {
                        for (Product product : products) {
                            if (product.getName() == String.valueOf(t.getValueAt(row, 0))) {
                                if (product.getActive()) {
                                    product.setActive(false);
                                    theManagerDB.updateProductAvailability(product.getId(), false);
                                    model.setValueAt("false", row, 1);
                                    t.revalidate();
                                    t.repaint();
                                }
                                else if (!product.getActive()) {
                                    product.setActive(true);
                                    theManagerDB.updateProductAvailability(product.getId(), true);
                                    model.setValueAt("true", row, 1);
                                    t.revalidate();
                                    t.repaint();
                                }
                            }
                        }
                    } else if (button == "Menus") {
                        for (Menu menu : menus) {
                            if (menu.getName() == String.valueOf(t.getValueAt(row, 0))) {
                                if (menu.getActive()) {
                                    menu.setActive(false);
                                    theManagerDB.updateMenuAvailability(menu.getId(), false);
                                    model.setValueAt("false", row, 1);
                                    t.revalidate();
                                    t.repaint();
                                }
                                else if (!menu.getActive()) {
                                    menu.setActive(true);
                                    theManagerDB.updateMenuAvailability(menu.getId(), true);
                                    model.setValueAt("true", row, 1);
                                    t.revalidate();
                                    t.repaint();
                                }
                            }
                        }
                    }
                }
            }
        });
    }    
}
