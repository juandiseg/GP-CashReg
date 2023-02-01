package windows.checksWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import util.abstractAddWindow;
import util.abstractUpdater;
import javax.swing.JLabel;

public class add_iWindow extends abstractAddWindow {

    private JLabel enterName = new JLabel("Enter the ingredient's NAME: ");
    private JLabel enterPrice = new JLabel("Enter the ingredients's PRICE: ");
    private JLabel enterAmount = new JLabel("Enter how many KGs per 'PRICE': ");
    private JTextField textFieldName = new JTextField();
    private JTextField textFieldPrice = new JTextField();
    private JTextField textFieldAmount = new JTextField();

    public add_iWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Ingredient", true);
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String price = textFieldPrice.getText();
                String amount = textFieldAmount.getText();
                if (checkInput(name, price, amount)) {
                    assist_add_iWindow tempWdw = new assist_add_iWindow(temp, name, amount, price);
                    tempWdw.updateToThisMenu();
                }
            }
        });
    }

    private boolean checkInput(String name, String price, String amount) {
        if (name.isEmpty() || price.isEmpty() || amount.isEmpty()) {
            printErrorGUI();
            return false;
        }
        return true;
    }

    @Override
    public void setBounds() {
        getInputSuccesful().setBounds(250, 120, 250, 25);
        getInputError().setBounds(250, 120, 300, 25);
        getAddButton().setBounds(80, 120, 130, 20);
        getBackButton().setBounds(400, 400, 120, 80);
        textFieldName.setBounds(210, 20, 165, 25);
        textFieldAmount.setBounds(210, 80, 165, 25);
        textFieldPrice.setBounds(210, 50, 165, 25);
        enterName.setBounds(10, 20, 200, 25);
        enterAmount.setBounds(10, 80, 200, 25);
        enterPrice.setBounds(10, 50, 200, 25);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getAddButton());
        theFrame.add(getBackButton());
        theFrame.add(enterName);
        theFrame.add(textFieldName);
        theFrame.add(enterPrice);
        theFrame.add(textFieldPrice);
        theFrame.add(enterAmount);
        theFrame.add(textFieldAmount);
    }

}
