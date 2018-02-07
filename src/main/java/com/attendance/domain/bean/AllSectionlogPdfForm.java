package com.attendance.domain.bean;

/**
 * Created by developer on 22/1/2561.
 */
public class AllSectionlogPdfForm {

    private String name;
    private String started;
    private String ended;
    private String day;
    private String time;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
