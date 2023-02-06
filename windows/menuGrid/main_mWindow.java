package windows.menuGrid;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class main_mWindow extends JFrame {
    
    private JButton button1 = new JButton("Products");
    private JButton button2 = new JButton("Menus");
    private JPanel panel = new JPanel();

    public main_mWindow(JPanel mainPanel) {
        panel.setPreferredSize(new Dimension(350, 550));
        panel.setLayout(new GridLayout());
        panel.add(button1);
        panel.add(button2);
        mainPanel.add(panel);
        addActionListeners();
    }

    private void addActionListeners() {
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                new categories_mWindow(panel);
                panel.revalidate();
                panel.repaint();
            }
        });
    }
}