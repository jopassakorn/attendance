package com.attendance.app.worklog;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by developer on 22/1/2561.
 */
public class SemesterlogAddForm {

    private int semester;
    private int year;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date started;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date ended;

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getEnded() {
        return ended;
    }

    public void setEnded(Date ended) {
        this.ended = ended;
    }
}
