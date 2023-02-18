package util;

import javax.swing.JPanel;
import iLayouts.iLayout;

public abstract class AbstractPanel {

    public static JPanel thePanel;
    public static ManagerDB theManagerDB;
    private AbstractPanel previousPanel;
    private iLayout layoutApplyer;

    public AbstractPanel(AbstractPanel previousPanel, iLayout layoutApplyer) {
        this.previousPanel = previousPanel;
        this.layoutApplyer = layoutApplyer;
    }

    final public void setPanel(JPanel _thePanel) {
        thePanel = _thePanel;
    }

    final public void setManagerDB(ManagerDB _theManagerDB) {
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

    public AbstractPanel getPreviousPanel() {
        return previousPanel;
    }
}