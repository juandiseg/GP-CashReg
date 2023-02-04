package windows.ordersWindow;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import iLayouts.GridLayoutApplyer;
import util.abstractUpdater;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class assist_add_oWindow extends abstractUpdater {

    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton toGoButton = new JButton("To go");
    private JButton backButton = new JButton("Back");
    private static ArrayList<Integer> freeTables = theManagerDB.getFreeTables();

    public assist_add_oWindow(abstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, freeTables.size()+2));
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Pick Table");
        // Adds a button for every table
        for (int i = 0; i < freeTables.size(); i++) {
            JButton button = new JButton("Table " + (freeTables.get(i)));
            theFrame.add(button);
            buttons.add(button);
        }
        theFrame.add(toGoButton);
        theFrame.add(backButton);
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        
        for (int i=0; i < freeTables.size(); i++) {
            int table_id = freeTables.get(i);
            buttons.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // add_oWindow tempWind = new add_oWindow(temp, table_id);
                    // tempWind.updateToThisMenu();
                }
            });
        }

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }
}
