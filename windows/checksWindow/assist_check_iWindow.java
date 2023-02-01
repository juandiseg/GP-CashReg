package windows.checksWindow;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import componentsFood.allergen;
import componentsFood.ingredient;
import componentsFood.provider;
import util.abstractEdit_CheckWindow;
import util.abstractUpdater;

public class assist_check_iWindow extends abstractEdit_CheckWindow {

    private String ingredientID;

    public assist_check_iWindow(abstractUpdater previousWindow, String ID) {
        super(previousWindow, "Allergens of selected ingredient", "Ingredient");
        this.ingredientID = ID;
    }

    @Override
    public void addRowsToModel() {
        myTable = new JTable();
        model = new DefaultTableModel();
        addEntriesToModel(setUpModel());
    }

    private int setUpModel() {
        ArrayList<allergen> allergenList = theManagerDB.getAllAllergens();
        model.addColumn("Ingredient");
        for (allergen temp : allergenList)
            model.addColumn(temp.getName());
        return allergenList.size();
    }

    public void addEntriesToModel(int totalAllergens) {
        String name = theManagerDB.getIngredientName(ingredientID);
        ArrayList<String> displayText = getDisplayValues(theManagerDB.getAllergensOfIngredient(ingredientID),
                totalAllergens);
        displayText.add(0, name);

        String[] tableFormatString = displayText.toArray(new String[0]);

        model.addRow(tableFormatString);
    }

    public ArrayList<String> getDisplayValues(ArrayList<String> containingOnes, int totalAllergens) {
        ArrayList<String> displayText = new ArrayList<>();
        for (int i = 1; i <= totalAllergens; i++) {
            if (containingOnes.contains(model.getColumnName(i)))
                displayText.add("Yes");
            else
                displayText.add("No");
        }
        return displayText;
    }

    @Override
    public void adjustTable() {
        setScrollPane(new JScrollPane(myTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        myTable.setModel(model);
        myTable.setDefaultEditor(Object.class, null);
        myTable.setFocusable(false);
    }

    @Override
    public void setBounds() {
        getSummaryTXT().setBounds(200, 20, 250, 25);
        getBackButton().setBounds(400, 400, 120, 80);
        getScrollPane().setBounds(45, 60, 500, 55);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getSummaryTXT());
        theFrame.add(getBackButton());
        theFrame.add(getScrollPane());
    }

    @Override
    public void addActionListeners() {
    }

}
