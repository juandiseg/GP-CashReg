package windows.availabilityWindow;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import componentsFood.product;
import util.abstractAddWindow;
import util.abstractUpdater;

import javax.swing.*;

public class assist_edit_productWindow extends abstractAddWindow {

    protected product theCurrentProduct;
    private JTextField textFieldName = new JTextField();
    private JScrollPane scrollPane;
    private JTable myTable;
    private DefaultTableModel model;
    private JLabel summaryTXT = new JLabel("Product to be changed:");
    private JLabel enterName = new JLabel("Enter the product's new NAME: ");
    private JLabel enterActive = new JLabel("Enter the product's new ACTIVE state: ");
    private JButton editOtherAttributes = new JButton("See more changes");
    private JToggleButton activeButton = new JToggleButton("Active");

    public assist_edit_productWindow(abstractUpdater previousWindow, product theCurrentProduct) {
        super(previousWindow, "Product", false);
        this.theCurrentProduct = theCurrentProduct;
        getAddButton().setText("Apply Changes");
    }

    @Override
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
                if (name.isEmpty())
                    return;
                boolean active = activeButton.getText().equals("Active");
                if (theManagerDB.easyUpdateProduct(theCurrentProduct.getId(), name, active))
                    updateTable();
                else
                    System.out.println("F");
            }
        });
        abstractUpdater temp = this;
        editOtherAttributes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                assist_assist_edit_productWindow tempWdw = new assist_assist_edit_productWindow(temp,
                        theCurrentProduct);
                tempWdw.updateToThisMenu();
            }
        });
        activeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (activeButton.getText().equals("Active"))
                    activeButton.setText("Non Active");
                else
                    activeButton.setText("Active");
            }
        });
    }

    private void updateTable() {
        theCurrentProduct = theManagerDB.getProduct(theCurrentProduct.getId());
        model.removeRow(0);
        String id = Integer.toString(theCurrentProduct.getId());
        String date = theCurrentProduct.getDate();
        String name = theCurrentProduct.getName();
        String price = Float.toString(theCurrentProduct.getPrice());
        String active = "No";
        if (theCurrentProduct.getActive())
            active = "Yes";
        model.addRow(new String[] { id, date, name, price, active });
    }

    private void loadTable() {
        myTable = new JTable();
        model = new DefaultTableModel(
                new String[] { "product_id", "Active Since", "Name", "Price", "Active" }, 0);
        myTable.setModel(model);
        String id = Integer.toString(theCurrentProduct.getId());
        String date = theCurrentProduct.getDate();
        String name = theCurrentProduct.getName();
        String price = Float.toString(theCurrentProduct.getPrice());
        String active = "No";
        if (theCurrentProduct.getActive())
            active = "Yes";
        model.addRow(new String[] { id, date, name, price, active });
        myTable.setDefaultEditor(Object.class, null);
        myTable.setFocusable(true);
        myTable.removeColumn(myTable.getColumn("product_id"));
        scrollPane = new JScrollPane(myTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    @Override
    public void setBounds() {
        getInputSuccesful().setBounds(230, 300, 300, 25);
        getInputError().setBounds(230, 300, 300, 25);
        getBackButton().setBounds(400, 400, 120, 80);
        getAddButton().setBounds(80, 400, 180, 20);
        editOtherAttributes.setBounds(80, 430, 180, 20);
        summaryTXT.setBounds(200, 20, 250, 25);
        textFieldName.setBounds(270, 130, 165, 25);
        enterName.setBounds(10, 130, 220, 25);
        scrollPane.setBounds(45, 60, 500, 55);
        activeButton.setBounds(270, 160, 170, 25);
        enterActive.setBounds(10, 160, 270, 25);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getBackButton());
        theFrame.add(getAddButton());
        theFrame.add(textFieldName);
        theFrame.add(summaryTXT);
        theFrame.add(enterName);
        theFrame.add(scrollPane);
        theFrame.add(activeButton);
        theFrame.add(enterActive);
        theFrame.add(editOtherAttributes);
    }

}
