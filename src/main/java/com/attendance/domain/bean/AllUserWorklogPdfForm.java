package com.attendance.domain.bean;

import java.util.List;

public class AllUserWorklogPdfForm {

    private String name;
    private String started;
    private String ended;
    private Integer totalLate;
    private Integer totalOntime;
    private Integer totalAbsent;
    private Integer totalPresent;
    private Boolean isSemesterReport;
    private String Semester;
    private String year;
    private List<WorklogPdfForm> worklogPdfFormList;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public Boolean getIsSemesterReport() {
        return isSemesterReport;
    }

    public void setIsSemesterReport(Boolean semesterReport) {
        isSemesterReport = semesterReport;
    }

    public Integer getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(Integer totalPresent) {
        this.totalPresent = totalPresent;
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

    public Integer getTotalLate() {
        return totalLate;
    }

    public void setTotalLate(Integer totalLate) {
        this.totalLate = totalLate;
    }

    public Integer getTotalOntime() {
        return totalOntime;
    }

    public void setTotalOntime(Integer totalOntime) {
        this.totalOntime = totalOntime;
    }

    public Integer getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(Integer totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public List<WorklogPdfForm> getWorklogPdfFormList() {
        return worklogPdfFormList;
    }

    public void setWorklogPdfFormList(List<WorklogPdfForm> worklogPdfFormList) {
        this.worklogPdfFormList = worklogPdfFormList;
    }
}
