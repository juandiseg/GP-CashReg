package windows.ordersWindow;

import java.awt.event.ActionListener;
import iLayouts.GridLayoutApplyer;
import util.AbstractUpdater;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class MainOWindow extends AbstractUpdater {

    private JButton button1 = new JButton("Add Order");
    private JButton button2 = new JButton("Delete Order");
    private JButton button3 = new JButton("Edit Order");
    private JButton button4 = new JButton("Check-out Order");
    private JButton backButton = new JButton("Back");

    public MainOWindow(AbstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, 5));
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Orders menu");
        theFrame.add(button1);
        theFrame.add(button2);
        theFrame.add(button3);
        theFrame.add(button4);
        theFrame.add(backButton);
    }

    @Override
    public void addActionListeners() {
        AbstractUpdater temp = this;
        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AssistAddOWindow tempWind = new AssistAddOWindow(temp);
                tempWind.updateToThisMenu();
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(theManagerDB.getOrders().size() == 0){
                    JOptionPane.showMessageDialog(theFrame, "There isn't any order at the moment", "No orders", JOptionPane.ERROR_MESSAGE);
                } else {
                    DeleteOWindow tempWinw = new DeleteOWindow(temp);
                    tempWinw.updateToThisMenu();
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(theManagerDB.getOrders().size() == 0){
                    JOptionPane.showMessageDialog(theFrame, "There isn't any order at the moment", "No orders", JOptionPane.ERROR_MESSAGE);
                } else {
                    EditOWindow tempWind = new EditOWindow(temp);
                    tempWind.updateToThisMenu();
                }
            }
        });
        
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(theManagerDB.getOrders().size() == 0){
                    JOptionPane.showMessageDialog(theFrame, "There isn't any order at the moment", "No orders", JOptionPane.ERROR_MESSAGE);
                } else {
                    AssistCheckOWindow tempWinw = new AssistCheckOWindow(temp);
                    tempWinw.updateToThisMenu();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }

}
