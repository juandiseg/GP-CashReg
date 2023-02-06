package windows.menuGrid;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import util.managerDB;

public class categories_mWindow extends JFrame {
    
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("<-");
    private managerDB theManagerDB = new managerDB();

    public categories_mWindow(JPanel mainPanel) {

        JPanel panel = new JPanel();
        panel.setPreferredSize(mainPanel.getPreferredSize());
        int numberButtons = theManagerDB.getAllCategories().size() + 1;
        if(((numberButtons / 2) % 1 == 0) && (numberButtons < 9))
            panel.setLayout(new GridLayout(numberButtons / 2, numberButtons / 2));
        else if((numberButtons / 3) % 1 == 0)
            panel.setLayout(new GridLayout(numberButtons / 3, numberButtons / 3));
        else
            panel.setLayout(new GridLayout(((int) (numberButtons / 2 + 0.5)), ((int) (numberButtons / 2 - 0.5))));

        for (int i = 0; i < theManagerDB.getAllCategories().size(); i++) {
            JButton button = new JButton();
            button.setText(theManagerDB.getAllCategories().get(i).getName());
            buttons.add(button);
        }

        for (int i = 0; i < buttons.size(); i++) {
			panel.add(buttons.get(i));
        }
        
        panel.add(backButton);
        mainPanel.add(panel);
    }
}