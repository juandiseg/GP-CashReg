package windows.availabilityWindow;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import componentsFood.product;
import util.abstractAddWindow;
import util.abstractUpdater;
import javax.swing.JLabel;

public class add_productWindow extends abstractAddWindow {

    private JLabel enterName = new JLabel("Enter the product's NAME: ");
    private JLabel enterPrice = new JLabel("Enter the product's PRICE: ");
    private JLabel enterActive = new JLabel("Is this product ACTIVE or NOT: ");
    private JTextField textFieldName = new JTextField();
    private JTextField textFieldPrice = new JTextField();
    private JToggleButton activeButton = new JToggleButton("Active");

    public add_productWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Product", true);
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String price = textFieldPrice.getText();
                Boolean active = true;
                if (activeButton.getText().equals("Non Active"))
                    active = false;
                if (checkInput(name, price)) {
                    LocalDate dateObj = LocalDate.now();
                    String date = dateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    product theResultProduct = theManagerDB.addProduct(date, name, Float.parseFloat(price), active);
                    if (theResultProduct != null) {
                        new assist_add_productWindow(temp, theResultProduct).updateToThisMenu();
                    } else {
                        System.out.println("error");
                    }
                }
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

    private boolean checkInput(String name, String price) {
        if (name.isEmpty() || price.isEmpty()) {
            printErrorGUI();
            return false;
        }
        return true;
    }

    @Override
    public void setBounds() {
        getInputSuccesful().setBounds(250, 120, 250, 25);
        getInputError().setBounds(250, 120, 300, 25);
        getAddButton().setBounds(80, 400, 130, 20);
        getBackButton().setBounds(400, 400, 120, 80);

        enterName.setBounds(10, 20, 200, 25);
        textFieldName.setBounds(210, 20, 165, 25);
        enterPrice.setBounds(10, 50, 200, 25);
        textFieldPrice.setBounds(210, 50, 165, 25);
        enterActive.setBounds(10, 80, 200, 25);
        activeButton.setBounds(210, 80, 170, 25);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getAddButton());
        theFrame.add(getBackButton());
        theFrame.add(enterName);
        theFrame.add(textFieldName);
        theFrame.add(enterActive);
        theFrame.add(activeButton);
        theFrame.add(enterPrice);
        theFrame.add(textFieldPrice);
    }
}
