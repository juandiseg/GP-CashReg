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

public class numberInput extends JFrame implements ActionListener {
	
	JTextField display;
	JButton numButton;
	JButton clearButton;
	String displayContent = "";
	String[] numPadContent = {"1","2","3","4","5","6","7","8","9",".","0","<-"};
	ArrayList<JButton> buttonList;
	
	// Keypad constructor class
	public numberInput(JPanel panel, JTextField display) {

		this.display = display;
		// sets the size of the Keypad display
		panel.setPreferredSize(new Dimension(320, 335));
		
		// initialize the buttonList
		buttonList = new ArrayList<JButton>(12);
		JPanel numberPanel = new JPanel();
		// set the numberPanel to have a 4row by 3col grid layout
		numberPanel.setLayout(new GridLayout(4,3,0,0));
		// set the size of the numberPanel
		numberPanel.setPreferredSize(new Dimension(320,260));

		// create the buttons and add them to the buttonList, properly displaying the numbers 
		for (int i = 0; i < numPadContent.length; i++) {
			numButton = new JButton(numPadContent[i]);
			buttonList.add(numButton);
		}
		
		// add the buttonList to the number panel
		for (int n = 0; n < buttonList.size(); n++) {
			buttonList.get(n).addActionListener(this);
			numberPanel.add(buttonList.get(n));
		}
		
		// add number panel to center part of display
		panel.add(numberPanel);
	}
	
	// update the display depending on clicked button(s)
	public void actionPerformed(ActionEvent e) {
		String textThere = display.getText();
		String additionalText = "";

		// add clicked number button text to display
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
		
		//create and set up the window.
		JPanel panel = new JPanel();
		
		//set up the content pane.
		panel.add(new numberInput(panel, display));
		panel.setVisible(true);

		return panel;
	}
}