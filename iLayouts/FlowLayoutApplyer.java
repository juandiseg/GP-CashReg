package iLayouts;

import java.awt.FlowLayout;
import javax.swing.JFrame;

public class FlowLayoutApplyer implements iLayout {

    private JFrame theFrame;

    public FlowLayoutApplyer(JFrame theFrame) {
        this.theFrame = theFrame;
    }

    public void applyLayout() {
        theFrame.setLayout(new FlowLayout());
    }
}