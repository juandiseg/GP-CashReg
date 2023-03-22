package util;

import javax.swing.JPanel;

// Abstract class to work with panels where you can go backwards
public abstract class AbstractPanel {

    public static JPanel thePanel;
    public static ManagerDB theManagerDB;
    private AbstractPanel previousPanel;

    public AbstractPanel() { }

    /**
     * Sets the main panel where everything will go
     * 
     * @param _thePanel the panel we want to make the main one
     */
    final public void setPanel(JPanel _thePanel) {
        thePanel = _thePanel;
    }

    /**
     * To set the manager for the database and be able to use the methods in ManagerDB which interacts with the databse
     * 
     * @param _theManagerDB manager DB we want to make the main one
     */
    final public void setManagerDB(ManagerDB _theManagerDB) {
        theManagerDB = _theManagerDB;
    }

    /**
     * To update the panel into another panel
     */
    public void updateToThisPanel() {
        deleteContents();
        addComponents();
        addActionListeners();
        refreshPanel();
    }

    /**
     * To delete all the contents of a panel
     */
    private void deleteContents() {
        thePanel.removeAll();
    }

    /**
     * To go back to the last panel you had
     */
    public void updateToPreviousPanel() {
        if (previousPanel == null)
            return;
        previousPanel.updateToThisPanel();
    }

    /**
     * To refresh the panel when you add/edit/delete something in the panel
     */
    private void refreshPanel() {
        thePanel.validate();
        thePanel.repaint();
    }

    /**
     * Abstract method to add new components into the panel
     */
    public abstract void addComponents();

    /**
     * Abstract method to have all the actionListeners for the panel
     */
    public abstract void addActionListeners();
}