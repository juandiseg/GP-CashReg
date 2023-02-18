package windows.checksWindow;

import java.awt.event.ActionListener;
import iLayouts.GridLayoutApplyer;
import util.AbstractUpdater;

import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class MainCWindow extends AbstractUpdater {

    private JButton button1 = new JButton("Check in");
    private JButton button2 = new JButton("Check out");
    private JButton backButton = new JButton("Back");

    public MainCWindow(AbstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, 3));
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Check-in/Check-out menu");
        theFrame.add(button1);
        theFrame.add(button2);
        theFrame.add(backButton);
    }

    @Override
    public void addActionListeners() {
        AbstractUpdater temp = this;
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CheckCWindow tempWind = new CheckCWindow(temp, button1.getText());
                tempWind.updateToThisMenu();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CheckCWindow tempWind = new CheckCWindow(temp, button2.getText());
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
