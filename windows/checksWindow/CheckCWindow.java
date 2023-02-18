package windows.checksWindow;

import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import iLayouts.FlowLayoutApplyer;
import objects.EmployeeSchedule;
import util.AbstractUpdater;
import util.NumberInput;

public class CheckCWindow extends AbstractUpdater {

    private String name;
    private JTextField textField = new JTextField(20);
    private JButton acceptButton = new JButton();
    private JButton backButton = new JButton("Back");

    public CheckCWindow(AbstractUpdater previousWindow, String name) {
        super(previousWindow, new FlowLayoutApplyer(theFrame));
        this.name = name;
    }

    @Override
    public void addComponents() {
        theFrame.setTitle(name);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.add(new JLabel("Enter ID"), BorderLayout.NORTH);
        panel2.add(textField, BorderLayout.SOUTH);
        panel.add(panel2, BorderLayout.NORTH);

        panel.add(keypad(), BorderLayout.CENTER);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        acceptButton.setText(name);
        panel3.add(backButton, BorderLayout.WEST);
        panel3.add(acceptButton, BorderLayout.EAST);
        panel.add(panel3, BorderLayout.SOUTH);
        
        theFrame.add(panel);
    }

    @Override
    public void addActionListeners() {
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int check = theManagerDB.checkEmployeeID(Integer.parseInt(textField.getText()));
                if ((check == 1) && (name == "Check in")) {
                    theManagerDB.checkIn(Integer.parseInt(textField.getText()));
                    theFrame.add(new JLabel("Check in successfully"));
                    theFrame.validate();
                    theFrame.repaint();
                }
                if ((check == 1) && (name == "Check out")) {
                    EmployeeSchedule schedule = theManagerDB.getEmployee_schedules(Integer.parseInt(textField.getText()));
                    LocalTime start_shift = LocalTime.parse(schedule.getStart_shift());
                    LocalTime end_shift = LocalTime.parse(schedule.getEnd_shift());
                    LocalTime realtime_in = LocalTime.parse(schedule.getRealtime_in());
                    LocalTime realtime_out = java.time.LocalTime.now();
                    
                    int shiftDuration = (int) ChronoUnit.MINUTES.between(start_shift, end_shift);
                    int realShift = (int) ChronoUnit.MINUTES.between(realtime_in, realtime_out);                    

                    if (realShift < shiftDuration) schedule.setUndertime(true);
                    else if (realShift >= shiftDuration) schedule.setUndertime(false);

                    theManagerDB.checkOut(Integer.parseInt(textField.getText()), realtime_out.toString(), schedule.getUndertime());
                    theFrame.add(new JLabel("Check out successfully"));
                    theFrame.validate();
                    theFrame.repaint();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
        
    }

    private JPanel keypad() {
        JPanel p = new JPanel();
        new NumberInput(p, textField);
        return p;
    }
    
}
