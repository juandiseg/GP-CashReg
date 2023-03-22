
package windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import util.AbstractPanel;
import util.TableInput;

// Class to display panel with all the items a chosen category has
public class PItems extends AbstractPanel {
    
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("<-");
    private TableInput table;
    private Tables panel2;
    private int numberItems;
    private int category_id;
    private int order_id;
    private int table_id;
    
    /**
     * Constructor of PItem
     * 
     * @param table table where all the order's information will be displayed
     * @param panel2 panel where all the items will appear
     * @param category_id category ID of the products category we are trying to display
     * @param order_id order ID of the current ID we are making
     * @param table_id table ID of the order we are making
     */
    public PItems(TableInput table, Tables panel2, int category_id, int order_id, int table_id) {
        this.table = table;
        this.panel2 = panel2;
        this.category_id = category_id;
        this.order_id = order_id;
        this.table_id = table_id;
        this.numberItems  = theManagerDB.getProductsByCategory(category_id).size();
        
        if (theManagerDB.getProductsByCategory(category_id).size()+1 < 3) thePanel.setLayout(new GridLayout(1, numberItems+1));
        else thePanel.setLayout(new GridLayout(2, (numberItems+1)/2));
    }

    @Override
    public void addComponents() {
        for (int i = 0; i < numberItems; i++) {
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
        
        for (int i = 0; i < numberItems; i++) {
            int product_id = theManagerDB.getProductsByCategory(category_id).get(i).getID();
            String product_name = theManagerDB.getProductsByCategory(category_id).get(i).getName();
            float product_price = theManagerDB.getProductsByCategory(category_id).get(i).getPrice();
            JButton button = buttons.get(i);
            
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (theManagerDB.newOrder(order_id)) {
                        // because it is a new order, we have to create a new entry in order_summary in the database
                        theManagerDB.addNewOrder(order_id);
                        theManagerDB.addOrderItem(order_id, product_id);
                        if (table_id != -1) {
                            theManagerDB.makeTableOccupied(order_id, table_id);
                            panel2.updateTablesPanel(table_id);
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
                    // when the order is added to the database we make this panel to go again to the options,
                    // so you don't have to be all the time going back
                    buttons.removeAll(buttons);
                    Options temp = new Options(table, panel2, thePanel, order_id, table_id);
                    temp.updateToThisPanel();
                    table.updateTable(product_id, product_name, product_price, true);
                }
            });
        }
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PCategories categories = new PCategories(table, panel2, order_id, table_id);
                categories.updateToThisPanel();
            }
        });
    }
}
