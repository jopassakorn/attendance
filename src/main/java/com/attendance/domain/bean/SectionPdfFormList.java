package com.attendance.domain.bean;

import com.attendance.domain.entity.Sectionlog;
import lombok.Data;

import java.util.List;

/**
 * Created by developer on 19/1/2561.
 */
public class SectionPdfFormList {

    private String name;
    private String semester;
    private String subjectName;
    private String started;
    private String ended;
    private String day;
    private String time;
    private String lateTimes;
    private String onTimes;
    private String absent;
    private String present;
    private List<SectionlogPdfForm> sectionlogPdfForms;

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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    public String getLateTimes() {
        return lateTimes;
    }

    public void setLateTimes(String lateTimes) {
        this.lateTimes = lateTimes;
    }

    public String getOnTimes() {
        return onTimes;
    }

    public void setOnTimes(String onTimes) {
        this.onTimes = onTimes;
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

    public List<SectionlogPdfForm> getSectionlogPdfForms() {
        return sectionlogPdfForms;
    }

    public void setSectionlogPdfForms(List<SectionlogPdfForm> sectionlogPdfForms) {
        this.sectionlogPdfForms = sectionlogPdfForms;
    }
}
