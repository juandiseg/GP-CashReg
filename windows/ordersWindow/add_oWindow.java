package windows.ordersWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import util.abstractAddWindow;
import util.abstractTable;
import util.abstractUpdater;
import javax.swing.JLabel;

public class add_oWindow extends abstractTable {

    private int order_id;

    public add_oWindow(abstractUpdater previousWindow, int order_id) {
        super(previousWindow, order_id);
        this.order_id = order_id;
    }
}
