package windows.availabilityWindow;

import java.awt.event.ActionListener;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import componentsFood.ingredient;
import componentsFood.product;
import util.abstractAddWindow;
import util.abstractUpdater;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class assist_add_productWindow extends abstractAddWindow {

    private JLabel selectIngredients = new JLabel("Select ingredients used: ");

    private JButton swapLeft = new JButton("Left");
    private JButton swapRight = new JButton("Right");
    private product theProduct;

    private JScrollPane scrollPaneIngredients;
    private JScrollPane scrollPaneSelected;
    private JTable tableIngredients;
    private JTable tableSelected;
    private DefaultTableModel modelIngredients;
    private DefaultTableModel modelSelected;

    public assist_add_productWindow(abstractUpdater previousWindow, product theProduct) {
        super(previousWindow, "Product", true);
        this.theProduct = theProduct;
    }

    @Override
    public void addComponents() {
        setTable();
        setBounds();
        addToFrame();
        setBackButton();
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Stack<ingredient> stackIngredients = new Stack<ingredient>();
                for (int i = 0; i < modelSelected.getRowCount(); i++) {
                    int ingID = Integer.parseInt((String) modelSelected.getValueAt(i, 0));
                    int provID = Integer.parseInt((String) modelSelected.getValueAt(i, 1));
                    String date = (String) modelSelected.getValueAt(i, 2);
                    String name = (String) modelSelected.getValueAt(i, 3);
                    Float price = Float.parseFloat((String) modelSelected.getValueAt(i, 4));
                    int amount = Integer.parseInt((String) modelSelected.getValueAt(i, 5));
                    boolean in_inventory = false;
                    if (((String) modelSelected.getValueAt(i, 6)).equals("Yes"))
                        in_inventory = true;
                    boolean active = false;
                    if (((String) modelSelected.getValueAt(i, 7)).equals("Yes"))
                        active = true;
                    stackIngredients
                            .push(new ingredient(ingID, provID, date, name, price, amount, in_inventory, active));
                }
                if (stackIngredients.empty()) {
                    getPreviousWindow().getPreviousWindow().updateToThisMenu();
                    return;
                }
                new assist_assist_add_productWindow(temp, theProduct, stackIngredients).updateToThisMenu();
            }
        });

        swapLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableIngredients.getSelectedRow();
                if (row == -1)
                    return;
                String ingID = (String) modelIngredients.getValueAt(row, 0);
                String provID = (String) modelIngredients.getValueAt(row, 1);
                String date = (String) modelIngredients.getValueAt(row, 2);
                String name = (String) modelIngredients.getValueAt(row, 3);
                String price = (String) modelIngredients.getValueAt(row, 4);
                String amount = (String) modelIngredients.getValueAt(row, 5);
                String in_inventory = (String) modelIngredients.getValueAt(row, 6);
                String active = (String) modelIngredients.getValueAt(row, 7);
                modelIngredients.removeRow(row);
                modelSelected.addRow(new String[] { ingID, provID, date, name, price, amount, in_inventory, active });
            }
        });
        swapRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableSelected.getSelectedRow();
                if (row == -1)
                    return;
                String ingID = (String) modelSelected.getValueAt(row, 0);
                String provID = (String) modelSelected.getValueAt(row, 1);
                String date = (String) modelSelected.getValueAt(row, 2);
                String name = (String) modelSelected.getValueAt(row, 3);
                String price = (String) modelSelected.getValueAt(row, 4);
                String amount = (String) modelSelected.getValueAt(row, 5);
                String in_inventory = (String) modelIngredients.getValueAt(row, 6);
                String active = (String) modelIngredients.getValueAt(row, 7);
                modelSelected.removeRow(row);
                modelIngredients
                        .addRow(new String[] { ingID, provID, date, name, price, amount, in_inventory, active });
            }
        });
    }

    @Override
    public void setBounds() {
        getInputSuccesful().setBounds(250, 120, 250, 25);
        getInputError().setBounds(250, 120, 300, 25);
        getAddButton().setBounds(80, 400, 130, 20);
        getBackButton().setBounds(400, 400, 120, 80);
        selectIngredients.setBounds(10, 50, 200, 25);
        scrollPaneIngredients.setBounds(320, 90, 170, 200);
        scrollPaneSelected.setBounds(50, 90, 170, 200);
        swapLeft.setBounds(230, 150, 80, 25);
        swapRight.setBounds(230, 190, 80, 25);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getAddButton());
        theFrame.add(getBackButton());
        theFrame.add(selectIngredients);
        theFrame.add(scrollPaneIngredients);
        theFrame.add(scrollPaneSelected);
        theFrame.add(swapRight);
        theFrame.add(swapLeft);
    }

    private void setTable() {
        tableIngredients = new JTable();
        tableIngredients.setDefaultEditor(Object.class, null);
        tableSelected = new JTable();
        tableSelected.setDefaultEditor(Object.class, null);
        modelIngredients = new DefaultTableModel(
                new String[] { "ingredient_id", "provider_id", "date", "Name", "Price", "Amount", "in_inventory",
                        "active" },
                0);
        modelSelected = new DefaultTableModel(
                new String[] { "ingredient_id", "provider_id", "date", "Name", "Price", "Amount", "in_inventory",
                        "active" },
                0);

        for (ingredient tempIngredient : theManagerDB.getAllCurrentIngredients()) {
            String ingID = Integer.toString(tempIngredient.getId());
            String provID = Integer.toString(tempIngredient.getProviderID());
            String date = tempIngredient.getDate();
            String name = tempIngredient.getName();
            String price = Float.toString(tempIngredient.getPrice());
            String amount = Integer.toString(tempIngredient.getAmount());
            String in_inventory = "No";
            if (tempIngredient.getInInventory())
                in_inventory = "Yes";
            String active = "No";
            if (tempIngredient.getActive())
                active = "Yes";

            modelIngredients.addRow(new String[] { ingID, provID, date, name, price, amount, in_inventory, active });
        }

        tableIngredients.setModel(modelIngredients);
        tableSelected.setModel(modelSelected);
        tableIngredients.removeColumn(tableIngredients.getColumn("ingredient_id"));
        tableIngredients.removeColumn(tableIngredients.getColumn("provider_id"));
        tableIngredients.removeColumn(tableIngredients.getColumn("date"));
        tableIngredients.removeColumn(tableIngredients.getColumn("in_inventory"));
        tableIngredients.removeColumn(tableIngredients.getColumn("active"));

        tableSelected.removeColumn(tableSelected.getColumn("ingredient_id"));
        tableSelected.removeColumn(tableSelected.getColumn("provider_id"));
        tableSelected.removeColumn(tableSelected.getColumn("date"));
        tableSelected.removeColumn(tableSelected.getColumn("in_inventory"));
        tableSelected.removeColumn(tableSelected.getColumn("active"));

        scrollPaneIngredients = new JScrollPane(tableIngredients, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneSelected = new JScrollPane(tableSelected, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

}
