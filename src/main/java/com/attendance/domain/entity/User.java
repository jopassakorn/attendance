package com.attendance.domain.entity;

import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.util.List;

/**
 * Created by developer on 3/1/2561.
 */

@Entity
@Table(name = "user")
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String finger;
    private boolean activated;

    private List<Section> sections;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    public List<Section> getSections(){return sections;}

    public void setSections(List<Section>sections){this.sections = sections;}

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "email")
    public String getEmail(){ return email;}

    public void setEmail(String email){ this.email = email;}

    @Column(name = "finger")
    public String getFinger(){ return finger;}

    public void setFinger(String finger){this.finger = finger;}

    @Column(name = "activated")
    public boolean getActivated(){ return activated;}

    public void setActivated(boolean activated){ this.activated = activated;}
}
