package iLayouts;

import java.awt.GridLayout;
import javax.swing.JFrame;

public class GridLayoutApplyer implements iLayout {

    private JFrame theFrame;
    private int numberOfButtons;

    public GridLayoutApplyer(JFrame theFrame, int numberOfButtons) {
        this.theFrame = theFrame;
        this.numberOfButtons = numberOfButtons;
    }

    public void applyLayout() {
        if(((numberOfButtons / 2) % 1 == 0) && (numberOfButtons < 9))
            theFrame.setLayout(new GridLayout(numberOfButtons / 2, numberOfButtons / 2));
        else if((numberOfButtons / 3) % 1 == 0)
            theFrame.setLayout(new GridLayout(numberOfButtons / 3, numberOfButtons / 3));
        else
            theFrame.setLayout(
                    new GridLayout(((int) (numberOfButtons / 2 + 0.5)), ((int) (numberOfButtons / 2 - 0.5))));
    }
}