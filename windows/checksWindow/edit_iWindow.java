package windows.checksWindow;

import javax.swing.table.DefaultTableModel;
import util.abstractEdit_CheckWindow;
import componentsFood.ingredient;
import util.abstractUpdater;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.*;

public class edit_iWindow extends abstractEdit_CheckWindow {

    public edit_iWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Choose Ingredient to be edited", "Ingredient");
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        myTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 1) { // to detect doble click events
                    try {
                        if (myTable.getValueAt(myTable.getSelectedRow(), 0).toString().equals(""))
                            return;
                        int ID = Integer.parseInt((String) model.getValueAt(myTable.getSelectedRow(), 0));
                        int prov_id = Integer.parseInt((String) model.getValueAt(myTable.getSelectedRow(), 1));
                        String date = (String) model.getValueAt(myTable.getSelectedRow(), 2);
                        new assist_edit_iWindow(temp, theManagerDB.getIngredient(ID, prov_id, date)).updateToThisMenu();
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }
                }
            }
        });
    }

    @Override
    public void addRowsToModel() {
        ArrayList<ingredient> tempList = theManagerDB.getAllCurrentIngredients();
        myTable = new JTable();
        model = new DefaultTableModel(
                new String[] { "ID", "Prov_ID", "Active Since", "Name", "Price", "Amount", "in_inventory", "active" },
                0);
        for (ingredient temp : tempList) {
            String id = Integer.toString(temp.getId());
            String prov_id = Integer.toString(temp.getProviderID());
            String price = Float.toString(temp.getPrice());
            String amount = Integer.toString(temp.getAmount());
            String in_inventory;
            String active;
            if (temp.getInInventory())
                in_inventory = "In inventory";
            else
                in_inventory = "Not in inventory";
            if (temp.getActive())
                active = "Active";
            else
                active = "Not active";
            model.addRow(
                    new String[] { id, prov_id, temp.getDate(), temp.getName(), price, amount, in_inventory, active });
        }
    }

    @Override
    public void adjustTable() {
        setScrollPane(new JScrollPane(myTable));
        getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        myTable.setDefaultEditor(Object.class, null);
        myTable.setFocusable(true);
        myTable.setModel(model);
        myTable.removeColumn(myTable.getColumn("ID"));
        myTable.removeColumn(myTable.getColumn("Prov_ID"));
    }

    @Override
    public void setBounds() {
        getScrollPane().setBounds(45, 60, 500, 300);
        getSummaryTXT().setBounds(200, 20, 250, 25);
        getBackButton().setBounds(400, 400, 120, 80);

    }

    @Override
    public void addToFrame() {
        theFrame.add(getSummaryTXT());
        theFrame.add(getBackButton());
        theFrame.add(getScrollPane());
    }

}
