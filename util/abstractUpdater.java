package util;

import javax.swing.JFrame;
import iLayouts.iLayout;

public abstract class AbstractUpdater {

    public static JFrame theFrame;
    public static ManagerDB theManagerDB;
    private AbstractUpdater previousWindow;
    private iLayout layoutApplyer;

    public AbstractUpdater(AbstractUpdater previousWindow, iLayout layoutApplyer) {
        this.previousWindow = previousWindow;
        this.layoutApplyer = layoutApplyer;
    }

    final public void setFrame(JFrame _theFrame) {
        theFrame = _theFrame;
    }

    final public void setManagerDB(ManagerDB _theManagerDB) {
        theManagerDB = _theManagerDB;
    }

    public void updateToThisMenu() {
        deleteContents();
        addComponents();
        addActionListeners();
        layoutApplyer.applyLayout();
        refreshFrame();
    }

    private void deleteContents() {
        theFrame.getContentPane().removeAll();
    }

    public void updateToPreviousMenu() {
        if (previousWindow == null)
            return;
        previousWindow.updateToThisMenu();
    }

    private void refreshFrame() {
        theFrame.validate();
        theFrame.repaint();
    }

    public abstract void addComponents();

    public abstract void addActionListeners();

    public AbstractUpdater getPreviousWindow() {
        return previousWindow;
    }
}