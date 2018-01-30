package com.attendance.domain.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by developer on 18/1/2561.
 */
@Entity
@Table(name = "section_log")
public class Sectionlog {

    private int id;
    private int sectionId;
    private String status;
    private String finger;
    private Date workDate;
    private int clockInSec;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId(){ return id;}

    public void setId(int id){ this.id = id;}

    @Column(name = "section_id")
    public int getSectionId(){ return sectionId;}

    public void setSectionId(int sectionId){ this.sectionId = sectionId;}

    @Column(name = "status")
    public String getStatus(){ return status;}

    public void setStatus(String status){ this.status = status;}

    @Column(name = "finger")
    public String getFinger(){return finger;}

    public void setFinger(String finger){ this.finger = finger;}

    @Column(name = "work_date")
    public Date getWorkDate(){return workDate;}

    public void setWorkDate(Date workDate){ this.workDate = workDate;}

    @Column(name = "clock_in_sec")
    public int getClockInSec(){return clockInSec;}

    public void setClockInSec(int clockInSec){ this.clockInSec = clockInSec;}
}
