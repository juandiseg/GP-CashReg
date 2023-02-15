package windows.menuPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import iLayouts.PanelGridLayout;
import util.abstractPanel;
import util.abstractUpdater;

public class categories_mWindow extends abstractPanel {
    
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton backButton = new JButton("<-");
    private int numberCategories = theManagerDB.getAllCategories().size();
    private abstractUpdater previousWindow;
    private int order_id;
    private int table_id;
    private Boolean newOrder;

    public categories_mWindow(abstractUpdater previousWindow, abstractPanel previousPanel, int order_id, int table_id, Boolean newOrder) {
        super(previousPanel, new PanelGridLayout(thePanel, theManagerDB.getAllCategories().size()+1));
        this.previousWindow = previousWindow;
        this.order_id = order_id;
        this.table_id = table_id;
        this.newOrder = newOrder;
    }

    @Override
    public void addComponents() {

        for (int i = 0; i < numberCategories; i++) {
            JButton button = new JButton();
            button.setText(theManagerDB.getAllCategories().get(i).getName());
            buttons.add(button);
        }

        for (int i = 0; i < buttons.size(); i++) {
			thePanel.add(buttons.get(i));
        }
        
        thePanel.add(backButton);        
    }

    @Override
    public void addActionListeners() {
        abstractPanel temp = this;

        for (int i = 0; i < numberCategories; i++) {
            int category_id = theManagerDB.getAllCategories().get(i).getID();
            JButton button = buttons.get(i);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    products_mWindow tempPanel = new products_mWindow(previousWindow, temp, category_id, order_id, table_id, newOrder);
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