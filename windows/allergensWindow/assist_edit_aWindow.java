package windows.allergensWindow;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import componentsFood.allergen;
import util.abstractAddWindow;
import util.abstractUpdater;
import javax.swing.JLabel;
import javax.swing.*;

public class assist_edit_aWindow extends abstractAddWindow {

    private JLabel summaryTXT = new JLabel("Allergen to be changed:");
    private JLabel enterName = new JLabel("Enter the new allergen's NAME: ");

    private allergen theCurrentAllergen;
    private JTextField textFieldName = new JTextField();
    private JTable myTable;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    public assist_edit_aWindow(abstractUpdater previousWindow, int ID) {
        super(previousWindow, "Allergen", false);
        theCurrentAllergen = theManagerDB.getAllergen(ID);
    }

    public void addComponents() {
        loadTable();
        setBounds();
        addToFrame();
        setBackButton();
    }

    @Override
    public void addActionListeners() {
        getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                if (name.isEmpty()) {
                    printErrorGUI();
                    return;
                }
                if (theManagerDB.editAllergen(theCurrentAllergen.getId(), name)) {
                    printSuccessGUI();
                    theCurrentAllergen = theManagerDB.getAllergen(theCurrentAllergen.getId());
                    model.removeRow(0);
                    model.addRow(new String[] { theCurrentAllergen.getName() });
                } else {
                    printErrorGUI();
                }
            }
        });
    }

    private void loadTable() {
        myTable = new JTable();
        model = new DefaultTableModel(new String[] { "Name" }, 0);
        myTable.setModel(model);
        model.addRow(new Object[] { theCurrentAllergen.getName() });
        myTable.setDefaultEditor(Object.class, null);
        myTable.setFocusable(true);
        scrollPane = new JScrollPane(myTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    @Override
    public void setBounds() {
        getInputSuccesful().setBounds(250, 160, 250, 25);
        getInputError().setBounds(250, 160, 300, 25);
        getBackButton().setBounds(400, 400, 120, 80);
        getAddButton().setBounds(80, 160, 130, 20);
        textFieldName.setBounds(200, 115, 165, 25);
        summaryTXT.setBounds(200, 20, 250, 25);
        enterName.setBounds(10, 115, 220, 25);
        scrollPane.setBounds(45, 60, 500, 40);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getBackButton());
        theFrame.add(getAddButton());
        theFrame.add(textFieldName);
        theFrame.add(summaryTXT);
        theFrame.add(enterName);
        theFrame.add(scrollPane);
    }

}
