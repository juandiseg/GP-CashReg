package util;

import javax.swing.JPanel;
import iLayouts.iLayout;

public abstract class abstractPanel {

    public static JPanel thePanel;
    public static managerDB theManagerDB;
    private abstractPanel previousPanel;
    private iLayout layoutApplyer;

    public abstractPanel(abstractPanel previousPanel, iLayout layoutApplyer) {
        this.previousPanel = previousPanel;
        this.layoutApplyer = layoutApplyer;
    }

    final public void setPanel(JPanel _thePanel) {
        thePanel = _thePanel;
    }

    final public void setManagerDB(managerDB _theManagerDB) {
        theManagerDB = _theManagerDB;
    }

    public void updateToThisPanel() {
        deleteContents();
        addComponents();
        addActionListeners();
        layoutApplyer.applyLayout();
        refreshFrame();
    }

    private void deleteContents() {
        thePanel.removeAll();
    }

    public void updateToPreviousPanel() {
        if (previousPanel == null)
            return;
        previousPanel.updateToThisPanel();
    }

    private void refreshFrame() {
        thePanel.validate();
        thePanel.repaint();
    }

    public abstract void addComponents();

    public abstract void addActionListeners();

    public abstractPanel getPreviousPanel() {
        return previousPanel;
    }
}