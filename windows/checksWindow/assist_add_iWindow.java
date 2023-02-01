package windows.checksWindow;

import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import componentsFood.provider;
import util.abstractAddWindow;
import util.abstractUpdater;
import java.time.LocalDate;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class assist_add_iWindow extends abstractAddWindow {

    private String name;
    private int amount;
    private String price;

    private JList<provider> theList = new JList<provider>();
    private JScrollPane scrollPane;

    private JLabel summaryTXT = new JLabel("Specify the following information:");
    private JLabel enterName = new JLabel("Is this ingredient ACTIVE or NOT: ");
    private JLabel enterEmail = new JLabel("Is this ingredient in inventory: ");
    private JLabel chooseProv = new JLabel("Which provider provides you this ingredient:");
    private JToggleButton activeButton = new JToggleButton("Active");
    private JToggleButton inventoryButton = new JToggleButton("With Inventory");

    public assist_add_iWindow(abstractUpdater previousWindow, String name, String amount, String price) {
        super(previousWindow, "Provider", true);
        this.name = name;
        this.amount = Integer.parseInt(amount);
        this.price = price;
    }

    @Override
    public void addComponents() {
        setList();
        setBounds();
        addToFrame();
        setBackButton();
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (theList.getSelectedValue() == null)
                    return;
                int providerID = theList.getSelectedValue().getId();
                boolean inventory = true;
                if (inventoryButton.getText().equals("Without Inventory"))
                    inventory = false;
                boolean active = true;
                if (activeButton.getText().equals("Non Active"))
                    active = false;
                LocalDate dateObj = LocalDate.now();
                String date = dateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int ID = theManagerDB.addIngredient(providerID, date, name, price, amount, inventory, active);
                if (ID != -1) {
                    assist_assist_add_iWindow tempWinw = new assist_assist_add_iWindow(temp, ID, name);
                    tempWinw.updateToThisMenu();
                } else
                    printErrorGUI();
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
        inventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (inventoryButton.getText().equals("With Inventory"))
                    inventoryButton.setText("Without Inventory");
                else
                    inventoryButton.setText("With Inventory");
            }
        });
    }

    @Override
    public void setBounds() {
        getInputSuccesful().setBounds(220, 360, 300, 25);
        getInputError().setBounds(220, 360, 300, 25);
        getBackButton().setBounds(400, 400, 120, 80);
        getAddButton().setBounds(80, 400, 130, 20);
        inventoryButton.setBounds(240, 90, 170, 25);
        activeButton.setBounds(240, 60, 170, 25);
        summaryTXT.setBounds(200, 20, 250, 25);
        enterEmail.setBounds(10, 90, 220, 25);

        chooseProv.setBounds(10, 120, 280, 25);
        scrollPane.setBounds(240, 150, 170, 200);

        enterName.setBounds(10, 60, 220, 25);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getBackButton());
        theFrame.add(inventoryButton);
        theFrame.add(getAddButton());
        theFrame.add(activeButton);
        theFrame.add(summaryTXT);
        theFrame.add(enterEmail);
        theFrame.add(chooseProv);
        theFrame.add(enterName);
        theFrame.add(scrollPane);
    }

    private void setList() {
        DefaultListModel<provider> listModel = new DefaultListModel<provider>();
        for (provider tempProv : theManagerDB.getAllProviders())
            listModel.addElement(tempProv);
        theList.setModel(listModel);
        scrollPane = new JScrollPane(theList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
