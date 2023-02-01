package windows.checksWindow;

import java.awt.event.ActionListener;
import iLayouts.GridLayoutApplyer;
import util.abstractUpdater;
import windows.allergensWindow.main_aWindow;

import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class main_iWindow extends abstractUpdater {

    private JButton button1 = new JButton("Add Ingredient");
    private JButton button2 = new JButton("Edit Ingredient");
    private JButton button3 = new JButton("NOT WORKING [Delete Ingredient]");
    private JButton button4 = new JButton("Check Ingredients");
    private JButton button5 = new JButton("Allergen Menu");
    private JButton backButton = new JButton("Back");

    public main_iWindow(abstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, 6));
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Ingredients menu");
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
                add_iWindow tempWind = new add_iWindow(temp);
                tempWind.updateToThisMenu();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edit_iWindow tempWind = new edit_iWindow(temp);
                tempWind.updateToThisMenu();
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check_iWindow tempWinw = new check_iWindow(temp);
                tempWinw.updateToThisMenu();
            }
        });
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main_aWindow tempWinw = new main_aWindow(temp);
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
