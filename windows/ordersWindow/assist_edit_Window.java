package windows.ordersWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import componentsFood.provider;
import util.abstractAddWindow;
import util.abstractUpdater;
import javax.swing.JLabel;
import javax.swing.*;

public class assist_edit_Window extends abstractAddWindow {

    private provider theCurrentProvider;
    private JTextField textFieldName = new JTextField();
    private JTextField textFieldEmail = new JTextField();
    private JTable myTable;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    private JLabel summaryTXT = new JLabel("Provider to be changed:");
    private JLabel enterEmail = new JLabel("Enter the new provider's EMAIL: ");
    private JLabel enterName = new JLabel("Enter the new provider's NAME: ");

    public assist_edit_Window(abstractUpdater previousWindow, int ID) {
        super(previousWindow, "Provider", false);
        theCurrentProvider = theManagerDB.getProvider(ID);
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
                String email = textFieldEmail.getText();
                if (name.isEmpty() && email.isEmpty()) {
                    printErrorGUI();
                    return;
                } else if (name.isEmpty() || email.isEmpty()) {
                    if (name.isEmpty())
                        theManagerDB.editProvider(theCurrentProvider.getId(), theCurrentProvider.getName(), email);
                    else
                        theManagerDB.editProvider(theCurrentProvider.getId(), name, theCurrentProvider.getEmail());
                    printSuccessGUI();
                    model.removeRow(0);
                    theCurrentProvider = theManagerDB.getProvider(theCurrentProvider.getId());
                    model.addRow(new String[] { theCurrentProvider.getName(), theCurrentProvider.getEmail() });
                    return;
                }
                if (theManagerDB.editProvider(theCurrentProvider.getId(), name, email)) {
                    printSuccessGUI();
                    theCurrentProvider = theManagerDB.getProvider(theCurrentProvider.getId());
                    model.removeRow(0);
                    model.addRow(new String[] { theCurrentProvider.getName(), theCurrentProvider.getEmail() });
                } else {
                    printErrorGUI();
                }

            }
        });
    }

    private void loadTable() {
        myTable = new JTable();
        model = new DefaultTableModel(new String[] { "Name", "Email" }, 0);
        myTable.setModel(model);
        model.addRow(new Object[] { theCurrentProvider.getName(), theCurrentProvider.getEmail() });
        myTable.setDefaultEditor(Object.class, null);
        myTable.setFocusable(true);
        scrollPane = new JScrollPane(myTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    @Override
    public void setBounds() {
        getInputSuccesful().setBounds(250, 175, 250, 25);
        getInputError().setBounds(250, 175, 300, 25);
        getBackButton().setBounds(400, 400, 120, 80);
        textFieldEmail.setBounds(200, 135, 165, 25);
        getAddButton().setBounds(80, 175, 130, 20);
        textFieldName.setBounds(200, 105, 165, 25);
        summaryTXT.setBounds(200, 20, 250, 25);
        enterEmail.setBounds(10, 135, 220, 25);
        enterName.setBounds(10, 105, 220, 25);
        scrollPane.setBounds(45, 60, 500, 40);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getAddButton());
        theFrame.add(getBackButton());
        theFrame.add(textFieldEmail);
        theFrame.add(textFieldName);
        theFrame.add(summaryTXT);
        theFrame.add(enterEmail);
        theFrame.add(enterName);
        theFrame.add(scrollPane);
    }

}
