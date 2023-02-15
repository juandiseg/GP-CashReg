package windows.menuPanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import iLayouts.PanelGridLayout;
import util.abstractPanel;
import util.abstractUpdater;
import util.managerDB;

public class main_mWindow extends abstractPanel {
    
    private JButton button1 = new JButton("Products");
    private JButton button2 = new JButton("Menus");
    static JPanel thePanel = new JPanel();
    private abstractUpdater previousWindow;
    private int order_id;
    private int table_id;
    private Boolean newOrder;

    public main_mWindow(abstractUpdater previousWindow, JPanel mainPanel, int order_id, int table_id, Boolean newOrder) {
        super(null, new PanelGridLayout(thePanel, 2));
        this.previousWindow = previousWindow;
        this.order_id = order_id;
        this.table_id = table_id;
        this.newOrder = newOrder;

        setPanel(thePanel);
        setManagerDB(new managerDB());
        thePanel.setPreferredSize(new Dimension(350, 550));
        thePanel.setVisible(true);
        mainPanel.add(thePanel);
        updateToThisPanel();
    }

    @Override
    public void addComponents() {
        thePanel.add(button1);
        thePanel.add(button2);
    }

    @Override
    public void addActionListeners() {
        abstractPanel temp = this;
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                categories_mWindow checkWdw = new categories_mWindow(previousWindow, temp, order_id, table_id, newOrder);
                checkWdw.updateToThisPanel();
            }
        });
        
    }
}