package windows.menuPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import iLayouts.PanelGridLayout;
import util.AbstractPanel;
import util.AbstractTable;

public class MenuProductsPanel extends AbstractPanel {
    
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("<-");
    private AbstractTable table;
    private AbstractPanel previousPanel;
    private int category_id;
    private int order_id;
    private int table_id;
    private int numberProducts;

    public MenuProductsPanel(AbstractTable table, AbstractPanel previousPanel, int category_id, int order_id, int table_id) {
        super(previousPanel, new PanelGridLayout(thePanel, theManagerDB.getMenusByCategory(category_id).size()+1));
        this.table = table;
        this.previousPanel = previousPanel;
        this.category_id = category_id;
        this.order_id = order_id;
        this.table_id = table_id;
        this.numberProducts = theManagerDB.getMenusByCategory(category_id).size();
    }

    @Override
    public void addComponents() {

        for (int i = 0; i < numberProducts; i++) {
            JButton button = new JButton();
            button.setText(theManagerDB.getMenusByCategory(category_id).get(i).getName());
            buttons.add(button);
        }

        for (int i = 0; i < buttons.size(); i++) {
			thePanel.add(buttons.get(i));
        }
        
        thePanel.add(backButton);        
    }

    @Override
    public void addActionListeners() {
        
        for (int i = 0; i < numberProducts; i++) {
            int menu_id = theManagerDB.getMenusByCategory(category_id).get(i).getId();
            String menu_name = theManagerDB.getMenusByCategory(category_id).get(i).getName();
            Float menu_price = theManagerDB.getMenusByCategory(category_id).get(i).getPrice();
            JButton button = buttons.get(i);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (theManagerDB.newOrder(order_id)) {
                        theManagerDB.addNewOrder(order_id, menu_id);
                        theManagerDB.addOrderMenu(order_id, menu_id);
                        if (table_id != -1) {
                            theManagerDB.makeTableOccupied(order_id, table_id);
                        }
                    }
                    else if (!theManagerDB.newOrder(order_id)) {
                        if (theManagerDB.checkMenuInOrder(menu_id, order_id)) {
                            theManagerDB.updateMenuQuantity(order_id, menu_id);
                        }
                        if (!theManagerDB.checkMenuInOrder(menu_id, order_id)) {
                            theManagerDB.addOrderMenu(order_id, menu_id);
                        }
                    }
                    buttons.removeAll(buttons);
                    previousPanel.updateToPreviousPanel();
                    // new add_oWindow(previousWindow, order_id, table_id, newOrder).updateToThisMenu();
                    table.updateTable(menu_id, menu_name, menu_price, false);
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