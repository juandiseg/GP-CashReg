package windows.ordersWindow;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import componentsFood.allergen;
import componentsFood.provider;
import util.abstractEdit_CheckWindow;
import util.abstractUpdater;

public class delete_oWindow extends abstractEdit_CheckWindow {

    private JLabel instruction = new JLabel("Double-click on Provider to be deleted");

    public delete_oWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Choose Provider to be deleted", "Provider");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addRowsToModel() {
        myTable = new JTable();
        model = new DefaultTableModel(new String[] { "ID", "Name", "Email" }, 0);
        ArrayList<provider> providerList = theManagerDB.getAllProviders();
        for (provider temp : providerList)
            model.addRow(new String[] { Integer.toString(temp.getId()), temp.getName(), temp.getEmail() });
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
                                "Are you sure you want to DELETE this PROVIDER?",
                                "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            // HAVE TO IMPLEMENT
                        }
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }
                }
            }
        });
    }

}
