package windows.allergensWindow;

import javax.swing.table.DefaultTableModel;
import componentsFood.allergen;
import util.abstractEdit_CheckWindow;
import util.abstractUpdater;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.*;

public class edit_aWindow extends abstractEdit_CheckWindow {

    public edit_aWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Choose Allergen to be edited", "Allergen");
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        myTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 1) { // to detect doble click events
                    try {
                        JTable target = (JTable) me.getSource();
                        if (target.getValueAt(target.getSelectedRow(), 0).toString().equals(""))
                            return;
                        String ID = (String) model.getValueAt(target.getSelectedRow(), 0);

                        new assist_edit_aWindow(temp, Integer.valueOf(ID)).updateToThisMenu();
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }
                }
            }
        });
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
