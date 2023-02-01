package windows.ordersWindow;

import javax.swing.table.DefaultTableModel;
import util.abstractEdit_CheckWindow;
import componentsFood.provider;
import util.abstractUpdater;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class check_oWindow extends abstractEdit_CheckWindow {

    public check_oWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Check Providers", "Provider");
    }

    @Override
    public void addActionListeners() {
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
    }

    @Override
    public void addToFrame() {
        theFrame.add(getSummaryTXT());
        theFrame.add(getBackButton());
        theFrame.add(getScrollPane());
    }

}
