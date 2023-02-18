package util;

import java.awt.Dimension;
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
	JButton numButton;
	JButton clearButton;
	String displayContent = "";
	String[] numPadContent = {"1","2","3","4","5","6","7","8","9",".","0","<-"};
	ArrayList<JButton> buttonList = new ArrayList<>();
	
	// Keypad constructor class
	public NumberInput(JPanel panel, JTextField display) {
		this.display = display;

		panel.setPreferredSize(new Dimension(320, 335));
		buttonList = new ArrayList<JButton>(12);
		JPanel numberPanel = new JPanel();
		numberPanel.setLayout(new GridLayout(4,3,0,0));
		numberPanel.setPreferredSize(new Dimension(320,260));

		for (int i = 0; i < numPadContent.length; i++) {
			numButton = new JButton(numPadContent[i]);
			buttonList.add(numButton);
		}
		
		for (int n = 0; n < buttonList.size(); n++) {
			buttonList.get(n).addActionListener(this);
			numberPanel.add(buttonList.get(n));
		}
		
		panel.add(numberPanel);
	}
	
	public void actionPerformed(ActionEvent e) {
		String textThere = display.getText();
		String additionalText = "";

		for (int i = 0; i < buttonList.size(); i++) {
			if (e.getSource().equals(buttonList.get(i))) {
				String txt = buttonList.get(i).getText();
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

	public JPanel setInput() {
		
		JPanel panel = new JPanel();
		
		panel.add(new NumberInput(panel, display));
		panel.setVisible(true);

		return panel;
	}
}