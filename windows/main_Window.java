package windows;

import windows.availabilityWindow.main_productWindow;
import windows.checksWindow.main_iWindow;
import windows.ordersWindow.*;
import windows.tablesWindow.*;

import java.awt.event.ActionListener;
import iLayouts.GridLayoutApplyer;
import java.awt.event.ActionEvent;

import util.abstractUpdater;
import util.managerDB;
import javax.swing.*;

public class main_Window extends abstractUpdater {

    static JFrame theFrame = new JFrame("Main Window");
    private JButton button1 = new JButton("Orders");
    private JButton button2 = new JButton("Check-in/Check-out");
    private JButton button3 = new JButton("Check availability");
    private JButton button4 = new JButton("Tables");

    public main_Window() {
        super(null, new GridLayoutApplyer(theFrame, 4));
        setFrame(theFrame);
        setManagerDB(new managerDB());
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setSize(600, 600);
        theFrame.setVisible(true);
        theFrame.setLocationRelativeTo(null);
        updateToThisMenu();
    }

    public void addComponents() {
        theFrame.add(button1);
        theFrame.add(button2);
        theFrame.add(button3);
        theFrame.add(button4);
    }

    public void addActionListeners() {
        abstractUpdater temp = this;

        // Orders button
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main_oWindow ordersWdw = new main_oWindow(temp);
                ordersWdw.updateToThisMenu();
            }
        });

        // Check-in/Check-out button
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main_iWindow checkWdw = new main_iWindow(temp);
                checkWdw.updateToThisMenu();
            }
        });

        // Products availability button
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main_productWindow availabilityWdw = new main_productWindow(temp);
                availabilityWdw.updateToThisMenu();
            }
        });

        // Tables button
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main_tWindow tableWdw = new main_tWindow(temp);
                tableWdw.updateToThisMenu();
            }
        });
    }

}