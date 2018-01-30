package com.attendance.domain.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by developer on 19/1/2561.
 */
@Entity
@Table(name = "semester_log")
public class Semesterlog {

    private int id;
    private int semester;
    private int year;
    private Date started;
    private Date ended;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    @Column(name = "semester")
    public int getSemester(){return semester;}

    public void setSemester(int semester){this.semester = semester;}

    @Column(name = "year")
    public int getYear(){return year;}

    public void setYear(int year){this.year = year;}

    @Column(name = "started")
    public Date getStarted(){return started;}

    public void setStarted(Date started){this.started = started;}

    @Column(name = "ended")
    public Date getEnded(){return ended;}

    public void setEnded(Date ended){this.ended = ended;}
}
