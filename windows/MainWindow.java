package windows;

import windows.availabilityWindow.MainAWindow;
import windows.checksWindow.MainCWindow;
import windows.ordersWindow.*;
import windows.tablesWindow.*;

import java.awt.event.ActionListener;
import iLayouts.GridLayoutApplyer;
import java.awt.event.ActionEvent;

import util.AbstractUpdater;
import util.ManagerDB;
import javax.swing.*;

public class MainWindow extends AbstractUpdater {

    static JFrame theFrame = new JFrame("Main Window");
    private JButton button1 = new JButton("Orders");
    private JButton button2 = new JButton("Check-in/Check-out");
    private JButton button3 = new JButton("Check availability");
    private JButton button4 = new JButton("Tables");

    public MainWindow() {
        super(null, new GridLayoutApplyer(theFrame, 4));
        setFrame(theFrame);
        setManagerDB(new ManagerDB());
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setSize(1000, 650);
        theFrame.setVisible(true);
        theFrame.setLocationRelativeTo(null);
        theFrame.setResizable(false);
        updateToThisMenu();
    }

    public void addComponents() {
        theFrame.add(button1);
        theFrame.add(button2);
        theFrame.add(button3);
        theFrame.add(button4);
    }

    public void addActionListeners() {
        AbstractUpdater temp = this;

        // Orders button
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainOWindow tempWind = new MainOWindow(temp);
                tempWind.updateToThisMenu();
            }
        });

        // Check-in/Check-out button
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainCWindow tempWind = new MainCWindow(temp);
                tempWind.updateToThisMenu();
            }
        });

        // Products availability button
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainAWindow tempWind = new MainAWindow(temp);
                tempWind.updateToThisMenu();
            }
        });

        // Tables button
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainTWindow tempWind = new MainTWindow(temp);
                tempWind.updateToThisMenu();
            }
        });
    }

}