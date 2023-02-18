package windows.menuPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import iLayouts.PanelGridLayout;
import util.AbstractPanel;
import util.AbstractTable;

public class ItemCategoriesPanel extends AbstractPanel {
    
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("<-");
    private int numberCategories = theManagerDB.getAllCategoriesProducts().size();
    private AbstractTable table;
    private int order_id;
    private int table_id;

    public ItemCategoriesPanel(AbstractTable table, AbstractPanel previousPanel, int order_id, int table_id) {
        super(previousPanel, new PanelGridLayout(thePanel, theManagerDB.getAllCategoriesProducts().size()+1));
        this.table = table;
        this.order_id = order_id;
        this.table_id = table_id;
    }

    @Override
    public void addComponents() {

        for (int i = 0; i < numberCategories; i++) {
            JButton button = new JButton();
            button.setText(theManagerDB.getAllCategoriesProducts().get(i).getName());
            buttons.add(button);
        }

        for (int i = 0; i < buttons.size(); i++) {
			thePanel.add(buttons.get(i));
        }
        
        thePanel.add(backButton);        
    }

    @Override
    public void addActionListeners() {
        AbstractPanel temp = this;

        for (int i = 0; i < numberCategories; i++) {
            int category_id = theManagerDB.getAllCategoriesProducts().get(i).getID();
            JButton button = buttons.get(i);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ItemProductsPanel tempPanel = new ItemProductsPanel(table, temp, category_id, order_id, table_id);
                    buttons.removeAll(buttons);
                    tempPanel.updateToThisPanel();
                }
            });
        }

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousPanel();
            }
        });
    }
}