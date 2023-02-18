package windows.menuPanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import iLayouts.PanelGridLayout;
import util.AbstractPanel;
import util.AbstractTable;
import util.ManagerDB;

public class MainMWindow extends AbstractPanel {
    
    private JButton button1 = new JButton("Products");
    private JButton button2 = new JButton("Menus");
    static JPanel thePanel = new JPanel();
    private AbstractTable table;
    private int order_id;
    private int table_id;

    public MainMWindow(AbstractTable table, JPanel mainPanel, int order_id, int table_id) {
        super(null, new PanelGridLayout(thePanel, 2));
        this.table = table;
        this.order_id = order_id;
        this.table_id = table_id;

        setPanel(thePanel);
        setManagerDB(new ManagerDB());
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
        AbstractPanel temp = this;

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ItemCategoriesPanel checkWdw = new ItemCategoriesPanel(table, temp, order_id, table_id);
                checkWdw.updateToThisPanel();
            }
        });
        
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuCategoriesPanel checkWdw = new MenuCategoriesPanel(table, temp, order_id, table_id);
                checkWdw.updateToThisPanel();
            }
        });
    }
}