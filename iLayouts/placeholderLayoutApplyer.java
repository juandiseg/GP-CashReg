package iLayouts;

import javax.swing.JFrame;

public class placeholderLayoutApplyer implements iLayout {

    public placeholderLayoutApplyer(JFrame theFrame) {
        theFrame.setLayout(null);
    }

    public void applyLayout() {
    }
}