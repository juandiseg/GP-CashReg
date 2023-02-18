package windows.availabilityWindow;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import iLayouts.FlowLayoutApplyer;
import objects.Menu;
import objects.Product;
import util.AbstractUpdater;
import util.ManagerDB;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class to create table where you can see the summary of an especific order
public class CheckAvailabilityWindow extends AbstractUpdater {

    private JTable t = new JTable() {
        public boolean isCellEditable(int row, int column){  
            return false;  
        }
    };
    private DefaultTableModel model = new DefaultTableModel(new String[] { "Product", "Availability" }, 0);
    private String button;
    private ArrayList<Product> products;
    private ArrayList<Menu> menus;
    private JButton backButton = new JButton("Back");
    private ManagerDB theManagerDB = new ManagerDB();
    
    public CheckAvailabilityWindow(AbstractUpdater previousWindow, String button) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.button = button;
        this.products = theManagerDB.getAllProducts();
        this.menus = theManagerDB.getAllMenus();
    }

    private void createTable(JPanel panel) {
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
        t.setBounds(30, 40, 200, 550);

        JScrollPane sp = new JScrollPane(t);

        panel.add(sp);
        panel.setVisible(true);
    }

    @Override
    public void addComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        createTable(panel);
        theFrame.add(panel);
        theFrame.add(backButton);
    }


    @Override
    public void addActionListeners() {
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

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }
}
