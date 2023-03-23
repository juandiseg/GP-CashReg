
package windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objects.EmployeeShift;

import javax.swing.BorderFactory;

import util.ManagerDB;
import util.NumberInput;

// Class that allows employees to check in and out
public class Check implements ActionListener {
    
    private ManagerDB theManagerDB = new ManagerDB();
    private JPanel panel1;
    private JLabel label = new JLabel("Insert your ID");
    private JTextField textField = new JTextField();
    private JButton button = new JButton("Ok");

    /**
     * Constructor fo Check
     * 
     * @param panel1 panel where the check will be displayed
     * @param numberInput keypad to enter the employee's ID
     */
    public Check(JPanel panel1, NumberInput numberInput) {
        this.panel1 = panel1;
        
        panel1.setPreferredSize(panel1.getSize());
        panel1.setBorder(BorderFactory.createEmptyBorder(120, 180, 130, 180));
        panel1.setLayout(new BorderLayout(10, 10));

        button.addActionListener(this);

        panel1.add(textField, BorderLayout.CENTER);
        panel1.add(label, BorderLayout.BEFORE_FIRST_LINE);
        panel1.add(button, BorderLayout.AFTER_LAST_LINE);
        
        numberInput.setDisplay(textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button) && !textField.getText().isEmpty()) {
            int employee_id = Integer.parseInt(textField.getText());
            Boolean check = theManagerDB.checkEmployeeID(employee_id);
            if (check) {
                EmployeeShift employee_shift = theManagerDB.getEmployee_shift(employee_id);
                if (employee_shift != null) {
                    // if the employee is checking in
                    if (employee_shift.getRealtime_in() == null) {
                        if (theManagerDB.checkIn(employee_id)) {
                            JOptionPane.showMessageDialog(null, "Check-in successfully", "Check-in", JOptionPane.INFORMATION_MESSAGE);
                            panel1.removeAll();
                            panel1.add(new JLabel("Welcome"));
                            panel1.revalidate();
                            panel1.repaint();
                        }
                    } 
                    // if the employee is checking out
                    else {
                        LocalTime start_shift = LocalTime.parse(employee_shift.getStart_shift());
                        LocalTime end_shift = LocalTime.parse(employee_shift.getEnd_shift());
                        LocalTime realtime_in = LocalTime.parse(employee_shift.getRealtime_in());
                        LocalTime realtime_out = java.time.LocalTime.now();
                        
                        int shiftDuration = (int) ChronoUnit.MINUTES.between(start_shift, end_shift);
                        int realShift = (int) ChronoUnit.MINUTES.between(realtime_in, realtime_out);                    

                        if (realShift < shiftDuration) employee_shift.setUndertime(true);
                        else if (realShift >= shiftDuration) employee_shift.setUndertime(false);

                        if (theManagerDB.checkOut(Integer.parseInt(textField.getText()), realtime_out.toString(), employee_shift.getUndertime())) {
                            JOptionPane.showMessageDialog(null, "Check-out successfully", "Check-out", JOptionPane.INFORMATION_MESSAGE);
                            panel1.removeAll();
                            panel1.add(new JLabel("Bye"), BorderLayout.CENTER);
                            panel1.revalidate();
                            panel1.repaint();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No shift founded", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}