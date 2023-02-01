package windows.checksWindow;

import java.util.Stack;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import componentsFood.allergen;
import util.abstractAddWindow;
import util.abstractUpdater;
import javax.swing.*;

public class assist_assist_add_iWindow extends abstractAddWindow {

    private int ID;

    private JScrollPane scrollPaneAllergen;
    private JScrollPane scrollPaneSelected;
    private JTable tableAllergens;
    private JTable tableSelected;

    private DefaultTableModel modelAllergens;
    private DefaultTableModel modelSelected;

    private JButton swapLeft = new JButton("Left");
    private JButton swapRight = new JButton("Right");

    private JLabel summaryTXT;

    public assist_assist_add_iWindow(abstractUpdater previousWindow, int ID, String name) {
        super(previousWindow, "Provider", true);
        summaryTXT = new JLabel("Select the allergens contained in " + name + ":");
        this.ID = ID;

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
                Stack<allergen> selectedAllergens = new Stack<allergen>();
                for (int i = 0; i < modelSelected.getRowCount(); i++) {
                    int ID = Integer.parseInt((String) modelSelected.getValueAt(i, 0));
                    String name = (String) modelSelected.getValueAt(i, 1);
                    selectedAllergens.push(new allergen(ID, name));
                }
                if (theManagerDB.addAlergensOfIngredient(selectedAllergens, ID)) {
                    add_iWindow tempWind = new add_iWindow(
                            temp.getPreviousWindow().getPreviousWindow().getPreviousWindow());
                    tempWind.updateToThisMenu();
                    return;
                } else
                    System.out.println("there was an error");
            }
        });
        swapLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableAllergens.getSelectedRow();
                if (row == -1)
                    return;
                String ID = (String) modelAllergens.getValueAt(row, 0);
                String name = (String) modelAllergens.getValueAt(row, 1);
                modelAllergens.removeRow(row);
                modelSelected.addRow(new String[] { ID, name });
            }
        });
        swapRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableSelected.getSelectedRow();
                if (row == -1)
                    return;
                String ID = (String) modelSelected.getValueAt(row, 0);
                String name = (String) modelSelected.getValueAt(row, 1);
                modelSelected.removeRow(row);
                modelAllergens.addRow(new String[] { ID, name });
            }
        });
    }

    @Override
    public void setBounds() {
        getInputSuccesful().setBounds(220, 360, 300, 25);
        getInputError().setBounds(220, 360, 300, 25);
        getBackButton().setBounds(400, 400, 120, 80);
        getAddButton().setBounds(80, 400, 130, 20);
        summaryTXT.setBounds(200, 20, 250, 25);
        scrollPaneAllergen.setBounds(320, 150, 170, 200);
        scrollPaneSelected.setBounds(50, 150, 170, 200);
        swapLeft.setBounds(230, 210, 80, 25);
        swapRight.setBounds(230, 250, 80, 25);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getBackButton());
        theFrame.add(getAddButton());
        theFrame.add(scrollPaneAllergen);
        theFrame.add(scrollPaneSelected);
        theFrame.add(summaryTXT);
        theFrame.add(swapRight);
        theFrame.add(swapLeft);
    }

    private void setTable() {
        tableAllergens = new JTable();
        tableSelected = new JTable();
        modelAllergens = new DefaultTableModel(new String[] { "ID", "Name" }, 0);
        modelSelected = new DefaultTableModel(new String[] { "ID", "Name" }, 0);
        for (allergen tempAllergen : theManagerDB.getAllAllergens())
            modelAllergens.addRow(new String[] { Integer.toString(tempAllergen.getId()), tempAllergen.getName() });
        tableAllergens.setModel(modelAllergens);
        tableSelected.setModel(modelSelected);
        tableAllergens.removeColumn(tableAllergens.getColumn("ID"));
        tableSelected.removeColumn(tableSelected.getColumn("ID"));
        scrollPaneAllergen = new JScrollPane(tableAllergens);
        scrollPaneAllergen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneSelected = new JScrollPane(tableSelected);
        scrollPaneSelected.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
}
