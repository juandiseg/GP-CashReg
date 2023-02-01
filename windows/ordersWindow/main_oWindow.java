package windows.ordersWindow;

import java.awt.event.ActionListener;
import iLayouts.GridLayoutApplyer;
import util.abstractUpdater;

import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class main_oWindow extends abstractUpdater {

    private JButton button1 = new JButton("Add Order");
    private JButton button2 = new JButton("Edit Order");
    private JButton button3 = new JButton("NOT WORKING [Delete Order]");
    private JButton button4 = new JButton("Check-out Order");
    private JButton backButton = new JButton("Back");

    public main_oWindow(abstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, 6));
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Providers menu");
        theFrame.add(button1);
        theFrame.add(button2);
        theFrame.add(button3);
        theFrame.add(button4);
        theFrame.add(backButton);
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_oWindow tempWind = new add_oWindow(temp);
                tempWind.updateToThisMenu();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edit_oWindow tempWind = new edit_oWindow(temp);
                tempWind.updateToThisMenu();
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delete_oWindow tempWinw = new delete_oWindow(temp);
                tempWinw.updateToThisMenu();
            }
        });
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check_oWindow tempWinw = new check_oWindow(temp);
                tempWinw.updateToThisMenu();
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }

}
