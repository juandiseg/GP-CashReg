package windows.menuPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import iLayouts.PanelGridLayout;
import util.AbstractPanel;
import util.AbstractTable;

public class ItemProductsPanel extends AbstractPanel {
    
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("<-");
    private AbstractTable table;
    private AbstractPanel previousPanel;
    private int category_id;
    private int order_id;
    private int table_id;
    private int numberProducts;

    public ItemProductsPanel(AbstractTable table, AbstractPanel previousPanel, int category_id, int order_id, int table_id) {
        super(previousPanel, new PanelGridLayout(thePanel, theManagerDB.getProductsByCategory(category_id).size()+1));
        this.table = table;
        this.previousPanel = previousPanel;
        this.category_id = category_id;
        this.order_id = order_id;
        this.table_id = table_id;
        this.numberProducts = theManagerDB.getProductsByCategory(category_id).size();
    }

    @Override
    public void addComponents() {

        for (int i = 0; i < numberProducts; i++) {
            JButton button = new JButton();
            button.setText(theManagerDB.getProductsByCategory(category_id).get(i).getName());
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
            int product_id = theManagerDB.getProductsByCategory(category_id).get(i).getId();
            String product_name = theManagerDB.getProductsByCategory(category_id).get(i).getName();
            float product_price = theManagerDB.getProductsByCategory(category_id).get(i).getPrice();
            JButton button = buttons.get(i);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (theManagerDB.newOrder(order_id)) {
                        theManagerDB.addNewOrder(order_id, product_id);
                        theManagerDB.addOrderItem(order_id, product_id);
                        if (table_id != -1) {
                            theManagerDB.makeTableOccupied(order_id, table_id);
                        }
                    }
                    else if (!theManagerDB.newOrder(order_id)) {
                        if (theManagerDB.checkProductInOrder(product_id, order_id)) {
                            theManagerDB.updateProductQuantity(order_id, product_id);
                        }
                        if (!theManagerDB.checkProductInOrder(product_id, order_id)) {
                            theManagerDB.addOrderItem(order_id, product_id);
                        }
                    }
                    buttons.removeAll(buttons);
                    previousPanel.updateToPreviousPanel();
                    // new add_oWindow(previousWindow, order_id, table_id, newOrder).updateToThisMenu();
                    table.updateTable(product_id, product_name, product_price, true);
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