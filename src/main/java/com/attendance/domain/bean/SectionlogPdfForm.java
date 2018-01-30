package com.attendance.domain.bean;

import lombok.Data;

/**
 * Created by developer on 19/1/2561.
 */

public class SectionlogPdfForm {

    private String workDate;
    private String status;
    private String lateMin;

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
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
}
