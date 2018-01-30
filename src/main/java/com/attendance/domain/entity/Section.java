package com.attendance.domain.entity;

import org.hibernate.exception.DataException;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by developer on 18/1/2561.
 */
@Entity
@Table(name="section")
public class Section {

    private int id;
    private int subjectId;
    private int userId;
    private int roomId;
    private int secStarted;
    private int day;
    private int semester;
    private int year;
    private Date started;
    private Date ended;

    private Subject subject;
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    public User getUser(){return user;}

    public void setUser(User user){this.user = user;}

    @ManyToOne
    @JoinColumn(name = "subject_id",insertable = false,updatable = false)
    public Subject getSubject(){return subject;}

    public void setSubject(Subject subject){this.subject = subject;}

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId(){ return id;}

    public void setId(int id){ this.id = id;}

    @Column(name = "subject_id")
    public int getSubjectId(){return subjectId;}

    public void setSubjectId(int subjectId){ this.subjectId = subjectId;}

    @Column(name = "user_id")
    public int getUserId(){return userId;}

    public void setUserId(int userId){this.userId = userId;}

    @Column(name = "room_id")
    public int getRoomId(){return roomId;}

    public void setRoomId(int roomId){this.roomId = roomId;}

    @Column(name = "sec_started")
    public int getSecStarted(){return secStarted;}

    public void setSecStarted(int secStarted){this.secStarted = secStarted;}

    @Column(name = "day")
    public int getDay(){return day;}

    public void setDay(int day){this.day = day;}

    @Column(name = "semester")
    public int getSemester(){return semester;}

    public void setSemester(int semester){this.semester = semester;}

    @Column(name = "year")
    public int getYear(){return year;}

    public void setYear(int year){ this.year = year;}

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "started")
    public Date getStarted(){return started;}

    public void setStarted(Date started){ this.started = started;}

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "ended")
    public Date getEnded(){return ended;}

    public void setEnded(Date ended){this.ended = ended;}
}
