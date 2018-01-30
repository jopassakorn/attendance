package com.attendance.app.section;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by developer on 18/1/2561.
 */
@Data
public class SectionAddForm {

    private int subjectId;
    private int userId;
    private int roomId;
    private int secStarted;
    private int day;
    private int semester;
    private int year;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private String started;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private String ended;

    public int getSubjectId(){return subjectId;}

    public void setSubjectId(int subjectId){ this.subjectId = subjectId;}

    public int getUserId(){return userId;}

    public void setUserId(int userId){this.userId = userId;}

    public int getRoomId(){return roomId;}

    public void setRoomId(int roomId){this.roomId = roomId;}

    public int getSecStarted(){return secStarted;}

    public void setSecStarted(int secStarted){this.secStarted = secStarted;}

    public int getDay(){return day;}

    public void setDay(int day){this.day = day;}

    public int getSemester(){return semester;}

    public void setSemester(int semester){this.semester = semester;}

    public int getYear(){return year;}

    public void setYear(int year){ this.year = year;}

    public String getStarted(){return started;}

    public void setStarted(String started){ this.started = started;}

    public String getEnded(){return ended;}

    public void setEnded(String ended){this.ended = ended;}
}
