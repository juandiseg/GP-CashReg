package windows.allergensWindow;

import javax.swing.table.DefaultTableModel;
import util.abstractEdit_CheckWindow;
import componentsFood.allergen;
import util.abstractUpdater;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class check_aWindow extends abstractEdit_CheckWindow {

    public check_aWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Check Allergens", "Allergen");
    }

    @Override
    public void addActionListeners() {
    }

    @Override
    public void addRowsToModel() {
        myTable = new JTable();
        model = new DefaultTableModel(new String[] { "ID", "Name" }, 0);
        ArrayList<allergen> temp = theManagerDB.getAllAllergens();
        for (allergen temp2 : temp)
            model.addRow(new String[] { Integer.toString(temp2.getId()), temp2.getName() });
    }

    @Override
    public void adjustTable() {
        setScrollPane(new JScrollPane(myTable));
        getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        myTable.setModel(model);
        myTable.removeColumn(myTable.getColumn("ID"));
        myTable.setDefaultEditor(Object.class, null);
        myTable.setFocusable(true);
    }

    @Override
    public void setBounds() {
        getSummaryTXT().setBounds(200, 20, 250, 25);
        getBackButton().setBounds(400, 400, 120, 80);
        getScrollPane().setBounds(45, 60, 500, 300);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getBackButton());
        theFrame.add(getSummaryTXT());
        theFrame.add(getScrollPane());
    }

}
