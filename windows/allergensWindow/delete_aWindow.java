package windows.allergensWindow;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import componentsFood.allergen;
import util.abstractEdit_CheckWindow;
import util.abstractUpdater;

public class delete_aWindow extends abstractEdit_CheckWindow {

    private JLabel instruction = new JLabel("Double-click on Allergen to be deleted");

    public delete_aWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Choose Allergen to be deleted", "Allergen");
        // TODO Auto-generated constructor stub
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
        instruction.setBounds(200, 370, 250, 25);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getBackButton());
        theFrame.add(getSummaryTXT());
        theFrame.add(getScrollPane());
        theFrame.add(instruction);
    }

    @Override
    public void addActionListeners() {
        myTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 1) { // to detect doble click events
                    try {
                        if (myTable.getValueAt(myTable.getSelectedRow(), 0).toString().equals(""))
                            return;
                        int row = myTable.getSelectedRow();
                        String ID = (String) model.getValueAt(row, 0);
                        int reply = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete this allergen?",
                                "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            if (theManagerDB.removeAllergen(ID)) {
                                model.removeRow(row);
                                theFrame.revalidate();
                            } else {
                                System.out.println("something went wrong with the DB");
                            }
                        }
                        // new assist_remove_aWindow(temp, Integer.valueOf(ID)).updateToThisMenu();

                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }
                }
            }
        });
    }

}
