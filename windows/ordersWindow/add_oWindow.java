package windows.ordersWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import util.abstractAddWindow;
import util.abstractUpdater;
import javax.swing.JLabel;

public class add_oWindow extends abstractAddWindow {

    private JLabel enterName = new JLabel("Enter the provider's NAME: ");
    private JLabel enterEmail = new JLabel("Enter the provider's EMAIL: ");
    private JTextField textFieldName = new JTextField();
    private JTextField textFieldEmail = new JTextField();

    public add_oWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Provider", true);
    }

    @Override
    public void addToFrame() {
        theFrame.add(enterName);
        theFrame.add(textFieldName);
        theFrame.add(enterEmail);
        theFrame.add(textFieldEmail);
        theFrame.add(getAddButton());
        theFrame.add(getBackButton());
    }

    @Override
    public void setBounds() {
        getAddButton().setBounds(80, 90, 130, 20);
        getBackButton().setBounds(400, 400, 120, 80);
        getInputSuccesful().setBounds(250, 90, 250, 25);
        getInputError().setBounds(250, 90, 300, 25);
        enterName.setBounds(10, 20, 160, 25);
        textFieldName.setBounds(180, 20, 165, 25);
        enterEmail.setBounds(10, 50, 160, 25);
        textFieldEmail.setBounds(180, 50, 165, 25);
    }

    @Override
    public void addActionListeners() {
        getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String email = textFieldEmail.getText();
                if (name.isEmpty() || email.isEmpty()) {
                    printErrorGUI();
                    return;
                }
                if (theManagerDB.addProvider(name, email)) {
                    printSuccessGUI();
                } else {
                    printErrorGUI();
                }
            }
        });
    }

}
