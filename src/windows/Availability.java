package windows;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import objects.Ingredient;
import util.ManagerDB;

public class Availability {

    private JTable t = new JTable() {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel model = new DefaultTableModel(new String[] { "Ingredient", "Inventory" }, 0);
    private ArrayList<Ingredient> ingredients;
    private JButton button1 = new JButton("Products");
    private JButton button2 = new JButton("Menus");
    private ManagerDB theManagerDB = new ManagerDB();
    private JPanel thePanel;

    public Availability(JPanel panel1, JPanel panel4) {
        thePanel = panel1;
        this.ingredients = theManagerDB.getAllIngredients();

        thePanel.setBorder(null);
        thePanel.setPreferredSize(panel1.getSize());
        thePanel.setLayout(new BorderLayout());

        addActionListeners();

        createTable();

        panel4.removeAll();
        panel4.add(button1);
        panel4.add(button2);
        panel4.revalidate();
        panel4.repaint();
    }

    private void createTable() {
        for (Ingredient tempIngr : ingredients) {
            String tempInv = "Yes";
            if (!tempIngr.getInventory())
                tempInv = "No";
            model.addRow(new String[] { tempIngr.getName(), tempInv });
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
        t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = t.getSelectedRow();
                Object[] options = { "Yes", "No" };
                int n = JOptionPane.showOptionDialog(null, "Do you want to change the state of invetory?", null,
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, t);
                if (n == 0) {
                    ingredients.get(row).setInventory(!ingredients.get(row).getInventory());
                    theManagerDB.updateIngredientInventory(ingredients.get(row), ingredients.get(row).getInventory());
                    String tempInv = "Yes";
                    if (!ingredients.get(row).getInventory())
                        tempInv = "No";
                    model.setValueAt(tempInv, row, 1);
                    t.revalidate();
                    t.repaint();
                }
            }
        });
    }
}
