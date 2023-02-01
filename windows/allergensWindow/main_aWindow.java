package windows.allergensWindow;

import java.awt.event.ActionListener;
import iLayouts.GridLayoutApplyer;
import java.awt.event.ActionEvent;
import util.abstractUpdater;
import javax.swing.*;

public class main_aWindow extends abstractUpdater {

    private JButton button1 = new JButton("Add Allergen");
    private JButton button2 = new JButton("Edit Allergen");
    private JButton button3 = new JButton("Delete Allergen");
    private JButton button4 = new JButton("Check Allergen");
    private JButton button5 = new JButton("Edit Ingredient's Allergens");
    private JButton backButton = new JButton("Back");

    public main_aWindow(abstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, 6));
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Allergens menu");
        theFrame.add(button1);
        theFrame.add(button2);
        theFrame.add(button3);
        theFrame.add(button4);
        theFrame.add(button5);
        theFrame.add(backButton);
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_aWindow tempWinw = new add_aWindow(temp);
                tempWinw.updateToThisMenu();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edit_aWindow tempWinw = new edit_aWindow(temp);
                tempWinw.updateToThisMenu();
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delete_aWindow tempWinw = new delete_aWindow(temp);
                tempWinw.updateToThisMenu();
            }
        });
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check_aWindow tempWinw = new check_aWindow(temp);
                tempWinw.updateToThisMenu();
            }
        });
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editIngr_aWindow tempWinw = new editIngr_aWindow(temp);
                tempWinw.updateToThisMenu();
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }
}