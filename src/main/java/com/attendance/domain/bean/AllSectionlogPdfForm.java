package com.attendance.domain.bean;

/**
 * Created by developer on 22/1/2561.
 */
public class AllSectionlogPdfForm {

    private String date;
    private String status;
    private String lateMin;
    private String className;

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

    public String getLateMin() {
        return lateMin;
    }

    public void setLateMin(String lateMin) {
        this.lateMin = lateMin;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
