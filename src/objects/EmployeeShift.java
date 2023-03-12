package objects;

public class EmployeeShift {

    private int employee_id;
    private String date;
    private String start_shift;
    private String end_shift;
    private String realtime_in;
    private String realtime_out;
    private Boolean undertime;

    public EmployeeShift(int employee_id, String date, String start_shift, String end_shift, String realtime_in, String realtime_out, Boolean undertime) {
        this.employee_id = employee_id;
        this.date = date;
        this.start_shift = start_shift;
        this.end_shift = end_shift;
        this.realtime_in = realtime_in;
        this.realtime_out = realtime_out;
        this.undertime = undertime;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public String getDate() {
        return date;
    }

    public String getStart_shift() {
        return start_shift;
    }

    public String getEnd_shift() {
        return end_shift;
    }

    public String getRealtime_in() {
        return realtime_in;
    }

    public String getRealtime_out() {
        return realtime_out;
    }

    public Boolean getUndertime() {
        return undertime;
    }

    public void setUndertime(Boolean undertime) {
        this.undertime = undertime;
    }
    
}
