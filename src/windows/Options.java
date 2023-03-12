
package windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import util.AbstractPanel;
import util.ManagerDB;
import util.TableInput;

public class Options extends AbstractPanel {
    
    private JButton button1 = new JButton("Products");
    private JButton button2 = new JButton("Menus");
    private JPanel thePanel = new JPanel();
    private TableInput table;
    private Tables panel2;
    private int order_id;
    private int table_id;
    
    public Options(TableInput table, Tables panel2, JPanel panel, int order_id, int table_id) {
        this.table = table;
        this.panel2 = panel2;
        this.order_id = order_id;
        this.table_id = table_id;
        
        thePanel = panel;
        setPanel(thePanel);
        setManagerDB(new ManagerDB());
        
        thePanel.setBorder(null);
        thePanel.setPreferredSize(panel.getPreferredSize());
        thePanel.setLayout(new GridLayout(1, 2));
        thePanel.setVisible(true);
        updateToThisPanel();
    }

    @Override
    public void addComponents() {
        thePanel.add(button1);
        thePanel.add(button2);
    }

    @Override
    public void addActionListeners() {
        if (order_id != -1) {

            button1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PCategories categories = new PCategories(table, panel2, order_id, table_id);
                    categories.updateToThisPanel();
                }
            });

            button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    MCategories categories = new MCategories(table, panel2, order_id, table_id);
                    categories.updateToThisPanel();
                }
            });
        }
    }

    public void setDefault() {
        new Options(null, null, thePanel, -1, table_id);
    }
}