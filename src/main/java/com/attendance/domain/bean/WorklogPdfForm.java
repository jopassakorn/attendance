package com.attendance.domain.bean;

/**
 * Created by developer on 22/1/2561.
 */
public class WorklogPdfForm {

    private String date;
    private String status;
    private String clockInTime;
    private String lateMin;
    private String classDay;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(String clockInTime) {
        this.clockInTime = clockInTime;
    }

    public String getLateMin() {
        return lateMin;
    }

    public void setLateMin(String lateMin) {
        this.lateMin = lateMin;
    }

    public String getClassDay() {
        return classDay;
    }

    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }
}
