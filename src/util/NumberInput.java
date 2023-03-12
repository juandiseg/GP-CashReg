package util;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NumberInput extends JFrame implements ActionListener {
	
    JTextField display;
    String[] numbers = {"1","2","3","4","5","6","7","8","9",".","0","<-"};
    ArrayList<JButton> buttons = new ArrayList<>();

	
    // Keypad constructor class
    public NumberInput(JPanel panel, JTextField display) {
        this.display = display;

        panel.setPreferredSize(panel.getPreferredSize());
        buttons = new ArrayList<JButton>(12);
        panel.setLayout(new GridLayout(4,3));

        for (String number : numbers) {
            buttons.add(new JButton(number));
        }

        for (JButton button : buttons) {
            button.addActionListener(this);
            panel.add(button);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (getDisplay() != null) {
            String textThere = display.getText();
            String additionalText = "";

            for (JButton button : buttons) {
                if (e.getSource().equals(button)) {
                    String txt = button.getText();
                    if(txt != "<-") {
                        additionalText = txt;
                    } else if((txt == "<-") && (textThere.length() > 0)){
                        StringBuffer sb = new StringBuffer(textThere);
                        sb.deleteCharAt(textThere.length()-1);
                        textThere = sb.toString();
                    }
                }
            }
            display.setText(textThere.concat(additionalText));
        }
    }

    public JTextField getDisplay() {
        return display;
    }

    public void setDisplay(JTextField display) {
        this.display = display;
    }
}