package com.attendance.domain.bean;

import java.util.List;

/**
 * Created by developer on 22/1/2561.
 */
public class WorklogPdfFormList {

    private String semester;
    private String name;
    private String lateTime;
    private String ontime;
    private String ongoing;
    private String absent;
    private String present;
    private List<WorklogPdfForm> worklogPdfForm;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorklogPdfForm> getWorklogPdfForm() {
        return worklogPdfForm;
    }

    public void setWorklogPdfForm(List<WorklogPdfForm> worklogPdfForm) {
        this.worklogPdfForm = worklogPdfForm;
    }

    public String getLateTime() {
        return lateTime;
    }

    public void setLateTime(String lateTime) {
        this.lateTime = lateTime;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }

    public String getOngoing() {
        return ongoing;
    }

    public void setOngoing(String ongoing) {
        this.ongoing = ongoing;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }
}
