package windows.checksWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import iLayouts.FlowLayoutApplyer;
import util.abstractUpdater;
import util.numberInput;

public class check_cWindow extends abstractUpdater {

    private String name;
    private JTextField textField = new JTextField(20);
    private JButton acceptButton = new JButton();
    private JButton backButton = new JButton("Back");

    public check_cWindow(abstractUpdater previousWindow, String name) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.name = name;
    }

    @Override
    public void addComponents() {
        theFrame.setTitle(name);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.add(new JLabel("Enter ID"), BorderLayout.NORTH);
        panel2.add(textField, BorderLayout.SOUTH);
        panel.add(panel2, BorderLayout.NORTH);

        panel.add(keypad(), BorderLayout.CENTER);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        acceptButton.setText(name);
        panel3.add(backButton, BorderLayout.WEST);
        panel3.add(acceptButton, BorderLayout.EAST);
        panel.add(panel3, BorderLayout.SOUTH);
        
        theFrame.add(panel);
    }

    @Override
    public void addActionListeners() {
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int check = theManagerDB.checkEmployeeID(Integer.parseInt(textField.getText()));
                if ((check == 1) && (name == "Check in")) {
                    // theManagerDB.checkIn(Integer.parseInt(textField.getText()));
                    // theFrame.add(new JLabel("Check in successfully"));
                    // theFrame.validate();
                    // theFrame.repaint();
                }
                if ((check == 1) && (name == "Check out")) {
                    // theManagerDB.checkOut(Integer.parseInt(textField.getText()));
                    // theFrame.add(new JLabel("Check in successfully"));
                    // theFrame.validate();
                    // theFrame.repaint();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
        
    }

    private JPanel keypad() {
        JPanel p = new JPanel();
        new numberInput(p, textField);
        return p;
    }
    
}
