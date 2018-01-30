package com.attendance.domain.bean;

import java.util.List;

/**
 * Created by developer on 22/1/2561.
 */
public class AllSectionPdfFormList {

    private String name;
    private String semester;
    private String totalLate;
    private String totalOntime;
    private String totalOngoing;
    private String totalAbsent;
    private String totalPresent;
    private List<AllSectionPdfForm> allSectionPdfForms;
    private List<AllSectionlogPdfForm> allSectionlogPdfForms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<AllSectionPdfForm> getAllSectionPdfForms() {
        return allSectionPdfForms;
    }

    public void setAllSectionPdfForms(List<AllSectionPdfForm> allSectionPdfForms) {
        this.allSectionPdfForms = allSectionPdfForms;
    }

    public List<AllSectionlogPdfForm> getAllSectionlogPdfForms() {
        return allSectionlogPdfForms;
    }

    public void setAllSectionlogPdfForms(List<AllSectionlogPdfForm> allSectionlogPdfForms) {
        this.allSectionlogPdfForms = allSectionlogPdfForms;
    }

    public String getTotalLate() {
        return totalLate;
    }

    public void setTotalLate(String totalLate) {
        this.totalLate = totalLate;
    }

    public String getTotalOntime() {
        return totalOntime;
    }

    public void setTotalOntime(String totalOntime) {
        this.totalOntime = totalOntime;
    }

    public String getTotalOngoing() {
        return totalOngoing;
    }

    public void setTotalOngoing(String totalOngoing) {
        this.totalOngoing = totalOngoing;
    }

    public String getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(String totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public String getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(String totalPresent) {
        this.totalPresent = totalPresent;
    }
}
