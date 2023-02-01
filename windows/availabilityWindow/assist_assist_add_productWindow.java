package windows.availabilityWindow;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class assist_assist_add_productWindow extends abstractAddWindow {

    private JLabel selectIngredients = new JLabel("Specify amount used of each ingredient: ");

    private product theProduct;
    private Stack<ingredient> stackIngredients;

    private JScrollPane scrollPaneSelected;
    private JTable tableSelected;
    private DefaultTableModel modelSelected;

    public assist_assist_add_productWindow(abstractUpdater previousWindow, product theProduct,
            Stack<ingredient> stackIngredients) {
        super(previousWindow, "Product", true);
        this.theProduct = theProduct;
        this.stackIngredients = stackIngredients;
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
        getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < modelSelected.getRowCount(); i++) {
                    int productID = theProduct.getId();
                    int ingredientID = Integer.parseInt((String) modelSelected.getValueAt(i, 0));
                    LocalDate dateObj = LocalDate.now();
                    String date = dateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    Float qty = Float.parseFloat((String) modelSelected.getValueAt(i, 6));
                    theManagerDB.addIngredientsToProduct(productID, ingredientID, date, qty);
                }
                System.out.println("done congrats");
                // new assist_assist_add_productWindow(temp, theProduct,
                // stackIngredients).updateToThisMenu();
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
        scrollPaneSelected.setBounds(50, 90, 170, 200);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getAddButton());
        theFrame.add(getBackButton());
        theFrame.add(selectIngredients);
        theFrame.add(scrollPaneSelected);
    }

    private void setTable() {
        tableSelected = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        modelSelected = new DefaultTableModel(
                new String[] { "ingredient_id", "provider_id", "date", "Name", "Price", "Amount Per Price",
                        "Amount Per Product", "in_inventory",
                        "active" },
                0);

        for (ingredient tempIngredient : stackIngredients) {
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

            modelSelected
                    .addRow(new String[] { ingID, provID, date, name, price, amount, "", in_inventory, active });
        }

        tableSelected.setModel(modelSelected);
        tableSelected.removeColumn(tableSelected.getColumn("ingredient_id"));
        tableSelected.removeColumn(tableSelected.getColumn("provider_id"));
        tableSelected.removeColumn(tableSelected.getColumn("date"));
        tableSelected.removeColumn(tableSelected.getColumn("in_inventory"));
        tableSelected.removeColumn(tableSelected.getColumn("active"));

        scrollPaneSelected = new JScrollPane(tableSelected, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

}
