package com.attendance.domain.bean;


import java.util.List;

public class AllUserSectionPdfForm {

    private String semesterYear;
    private String started;
    private String ended;
    private String name;
    private Integer totalOntime;
    private Integer totalLate;
    private Integer totalOngoing;
    private Integer totalAbsent;
    private Integer totalPresent;
    private String subjectName;
    private Boolean isSemesterReport;

    private List<SectionlogPdfForm> sectionlogPdfForm;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Boolean getIsSemesterReport() {
        return isSemesterReport;
    }

    public void setIsSemesterReport(Boolean semesterReport) {
        isSemesterReport = semesterReport;
    }

    public String getSemesterYear() {
        return semesterYear;
    }

    public void setSemesterYear(String semesterYear) {
        this.semesterYear = semesterYear;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalOntime() {
        return totalOntime;
    }

    public void setTotalOntime(Integer totalOntime) {
        this.totalOntime = totalOntime;
    }

    public Integer getTotalLate() {
        return totalLate;
    }

    public void setTotalLate(Integer totalLate) {
        this.totalLate = totalLate;
    }

    public Integer getTotalOngoing() {
        return totalOngoing;
    }

    public void setTotalOngoing(Integer totalOngoing) {
        this.totalOngoing = totalOngoing;
    }

    public Integer getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(Integer totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public Integer getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(Integer totalPresent) {
        this.totalPresent = totalPresent;
    }

    public List<SectionlogPdfForm> getSectionlogPdfForm() {
        return sectionlogPdfForm;
    }

    public void setSectionlogPdfForm(List<SectionlogPdfForm> sectionlogPdfForm) {
        sectionlogPdfForm = sectionlogPdfForm;
    }

}
