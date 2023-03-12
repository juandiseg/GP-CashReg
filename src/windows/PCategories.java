
package windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import util.AbstractPanel;
import util.TableInput;

public class PCategories extends AbstractPanel {
    
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("<-");
    private int numberCategories;
    private TableInput table;
    private Tables panel2;
    private int order_id;
    
    public PCategories(TableInput table, Tables panel2, int order_id) {
        this.table = table;
        this.panel2 = panel2;
        this.order_id = order_id;
        
        numberCategories = theManagerDB.getAllCategoriesProducts().size();
        
        if (theManagerDB.getAllCategoriesProducts().size()+1 < 3) thePanel.setLayout(new GridLayout(1, theManagerDB.getAllCategoriesProducts().size()+1));
        else thePanel.setLayout(new GridLayout(2, (theManagerDB.getAllCategoriesProducts().size()+1)/2));
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
        for (int i = 0; i < numberCategories; i++) {
            int category_id = theManagerDB.getAllCategoriesProducts().get(i).getID();
            JButton button = buttons.get(i);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PItems tempPanel = new PItems(table, panel2, category_id, order_id);
                    buttons.removeAll(buttons);
                    tempPanel.updateToThisPanel();
                }
            });
        }
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Options temp = new Options(table, panel2, thePanel, order_id);
                temp.updateToThisPanel();
            }
        });
    }
}
