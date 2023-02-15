package iLayouts;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class PanelGridLayout implements iLayout {

    private JPanel thePanel;
    private int numberOfButtons = 0;

    public PanelGridLayout(JPanel thePanel, int numberOfButtons) {
        this.thePanel = thePanel;
        this.numberOfButtons = numberOfButtons;
    }

    public void applyLayout() {
        if(numberOfButtons == 2)
            thePanel.setLayout(new GridLayout(2, 1));
        else if(numberOfButtons == 3)
            thePanel.setLayout(new GridLayout(3, 1));
        else if((numberOfButtons / 2) % 1 == 0)
            thePanel.setLayout(new GridLayout(numberOfButtons / 2, 2));
        else
            thePanel.setLayout(new GridLayout(((int) (numberOfButtons / 2 + 0.5)), 2));
    }
}