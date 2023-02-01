package util;

import iLayouts.placeholderLayoutApplyer;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public abstract class abstractEdit_CheckWindow extends abstractUpdater {

    private JButton backButton = new JButton("Back");
    private JScrollPane scrollPane;
    private JLabel summaryTXT;
    protected DefaultTableModel model;
    protected JTable myTable;

    public abstractEdit_CheckWindow(abstractUpdater previousWindow, String title, String name) {
        super(previousWindow, new placeholderLayoutApplyer(theFrame));
        theFrame.setTitle(title);
        summaryTXT = new JLabel("Summary of current " + name + ":");
    }

    public void addComponents() {
        setTable();
        setBounds();
        addToFrame();
        setBackButton();
    }

    private void setTable() {
        addRowsToModel();
        adjustTable();
    }

    public abstract void addRowsToModel();

    public abstract void adjustTable();

    public abstract void setBounds();

    public abstract void addToFrame();

    private void setBackButton() {
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }

    public JLabel getSummaryTXT() {
        return summaryTXT;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane newScrollPane) {
        this.scrollPane = newScrollPane;
    }

}
