package windows.availabilityWindow;

import java.awt.event.ActionListener;
import iLayouts.GridLayoutApplyer;
import util.AbstractUpdater;

import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class MainAWindow extends AbstractUpdater {

    private JButton button1 = new JButton("Products");
    private JButton button2 = new JButton("Menus");
    private JButton backButton = new JButton("Back");

    public MainAWindow(AbstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, 3));
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Products Availability Window");
        theFrame.add(button1);
        theFrame.add(button2);
        theFrame.add(backButton);
    }

    @Override
    public void addActionListeners() {
        AbstractUpdater temp = this;

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CheckAvailabilityWindow tempWind = new CheckAvailabilityWindow(temp, "Products");
                tempWind.updateToThisMenu();
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CheckAvailabilityWindow tempWind = new CheckAvailabilityWindow(temp, "Menus");
                tempWind.updateToThisMenu();
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }

}
