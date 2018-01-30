package com.attendance.domain.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by developer on 19/1/2561.
 */
@Entity
@Table(name = "holiday")
public class Holiday {

    private int id;
    private String name;
    private Date date;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId(){return id;}

    public void setId(int id){ this.id = id;}

    @Column(name = "holiday_name")
    public String getName(){ return name;}

    public void setName(String name){ this.name = name;}

    @Column(name = "holiday_date")
    public Date getDate(){return date;}

    public void setDate(Date date){ this.date = date;}
}
