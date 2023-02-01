package windows.allergensWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import util.abstractAddWindow;
import util.abstractUpdater;
import javax.swing.JLabel;

public class add_aWindow extends abstractAddWindow {

    private JLabel enterName = new JLabel("Enter the allergen's NAME: ");
    private JTextField textFieldName = new JTextField();

    public add_aWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Allergen", true);
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
                if (theManagerDB.addAllergen(name)) {
                    printSuccessGUI();
                    return;
                }
                printErrorGUI();
                return;
            }
        });
    }

    @Override
    public void setBounds() {
        getInputSuccesful().setBounds(250, 90, 250, 25);
        getInputError().setBounds(250, 90, 300, 25);
        getAddButton().setBounds(80, 90, 130, 20);
        getBackButton().setBounds(400, 400, 120, 80);
        textFieldName.setBounds(180, 20, 165, 25);
        enterName.setBounds(10, 20, 160, 25);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getBackButton());
        theFrame.add(getAddButton());
        theFrame.add(textFieldName);
        theFrame.add(enterName);

    }

}
