package com.attendance.domain.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by developer on 19/1/2561.
 */
@Entity
@Table(name = "work_log")
public class Worklog {

    private int id;
    private int userId;
    private String status;
    private String finger;
    private Date date;
    private int clockInSec;
    private Time clockInTime;
    private String note;
    private boolean isNoteWrttten;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId(){ return  id;}

    public void setId(int id){ this.id = id;}

    @Column(name = "user_id")
    public int getUserId(){ return userId;}

    public void setUserId(int userId){ this.userId = userId;}

    @Column(name = "status")
    public String getStatus(){return status;}

    public void setStatus(String status){ this.status = status;}

    @Column(name = "finger")
    public String getFinger(){return finger;}

    public void setFinger(String finger){this.finger = finger;}

    @Column(name = "work_date")
    public Date getDate(){return date;}

    public void setDate(Date date){this.date = date;}

    @Column(name = "clock_in_sec")
    public int getClockInSec(){return clockInSec;}

    public void setClockInSec(int clockInSec){this.clockInSec = clockInSec;}

    @Column(name = "clock_in_time")
    public Time getClockInTime(){return clockInTime;}

    public void setClockInTime(Time clockInTime){this.clockInTime = clockInTime;}

    @Column(name = "note")
    public String getNote(){return note;}

    public void setNote(String note){this.note = note;}

    @Column(name = "is_note_wrttten")
    public boolean getIsNoteWritten(){ return isNoteWrttten;}

    public void setIsNoteWritten(boolean isNoteWrttten){ this.isNoteWrttten = isNoteWrttten;}
}
