package windows.ordersWindow;

import javax.swing.table.DefaultTableModel;
import componentsFood.provider;
import util.abstractEdit_CheckWindow;
import util.abstractTable;
import util.abstractUpdater;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.*;

public class edit_oWindow extends abstractTable {

    private int order_id;
    
    public edit_oWindow(abstractUpdater previousWindow, int order_id) {
        super(previousWindow, order_id);
        this.order_id = order_id;
    }
}
