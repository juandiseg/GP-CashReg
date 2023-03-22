
package windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import util.AbstractPanel;
import util.TableInput;

// Class to dispaly all the items a product's category has
public class MItems extends AbstractPanel {
    
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("<-");
    private TableInput table;
    private Tables panel2;
    private int numberItems;
    private int category_id;
    private int order_id;
    private int table_id;
    
    /**
     * Constructor for MItems
     * 
     * @param table table where the order's information is
     * @param panel2 panel where the tables are
     * @param category_id category ID of the items you want
     * @param order_id order ID of the current order you are making
     * @param table_id table ID of the order you are making
     */
    public MItems(TableInput table, Tables panel2, int category_id, int order_id, int table_id) {
        this.table = table;
        this.panel2 = panel2;
        this.category_id = category_id;
        this.order_id = order_id;
        this.table_id = table_id;
        this.numberItems  = theManagerDB.getMenusByCategory(category_id).size();
        
        if (theManagerDB.getMenusByCategory(category_id).size()+1 < 3) thePanel.setLayout(new GridLayout(1, numberItems+1));
        else thePanel.setLayout(new GridLayout(2, (numberItems+1)/2));
    }

    @Override
    public void addComponents() {
        for (int i = 0; i < numberItems; i++) {
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
        
        for (int i = 0; i < numberItems; i++) {
            int menu_id = theManagerDB.getMenusByCategory(category_id).get(i).getId();
            String menu_name = theManagerDB.getMenusByCategory(category_id).get(i).getName();
            float menu_price = theManagerDB.getMenusByCategory(category_id).get(i).getPrice();
            JButton button = buttons.get(i);
            
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (theManagerDB.newOrder(order_id)) {
                        int table_id = theManagerDB.getTableID(order_id);
                        theManagerDB.addNewOrder(order_id);
                        theManagerDB.addOrderMenu(order_id, menu_id);
                        if (table_id != -1) {
                            theManagerDB.makeTableOccupied(order_id, table_id);
                            panel2.updateTablesPanel(table_id);
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
                    Options temp = new Options(table, panel2, thePanel, order_id, table_id);
                    temp.updateToThisPanel();
                    table.updateTable(menu_id, menu_name, menu_price, false);
                }
            });
        }
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MCategories categories = new MCategories(table, panel2, order_id, table_id);
                categories.updateToThisPanel();
            }
        });
    }
}
