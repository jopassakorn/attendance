package com.attendance.app.user;

import com.attendance.domain.entity.Section;

import javax.persistence.*;
import java.util.List;

public class UserResultShowForm {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String finger;
    private int activatedCode;
    private int totalWorkLate;
    private int totalWorkOntime;
    private int totalWorkAbsent;
    private int totalClassLate;
    private int totalClassOntime;
    private int totalClassAbsent;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){ return email;}

    public void setEmail(String email){ this.email = email;}

    public String getFinger(){ return finger;}

    public void setFinger(String finger){this.finger = finger;}

    public int getActivatedCode() {
        return activatedCode;
    }

    public void setActivatedCode(int activatedCode) {
        this.activatedCode = activatedCode;
    }

    public int getTotalWorkLate() {
        return totalWorkLate;
    }

    public void setTotalWorkLate(int totalWorkLate) {
        this.totalWorkLate = totalWorkLate;
    }

    public int getTotalWorkOntime() {
        return totalWorkOntime;
    }

    public void setTotalWorkOntime(int totalWorkOntime) {
        this.totalWorkOntime = totalWorkOntime;
    }

    public int getTotalWorkAbsent() {
        return totalWorkAbsent;
    }

    public void setTotalWorkAbsent(int totalWorkAbsent) {
        this.totalWorkAbsent = totalWorkAbsent;
    }

    public int getTotalClassLate() {
        return totalClassLate;
    }

    public void setTotalClassLate(int totalClassLate) {
        this.totalClassLate = totalClassLate;
    }

    public int getTotalClassOntime() {
        return totalClassOntime;
    }

    public void setTotalClassOntime(int totalClassOntime) {
        this.totalClassOntime = totalClassOntime;
    }

    public int getTotalClassAbsent() {
        return totalClassAbsent;
    }

    public void setTotalClassAbsent(int totalClassAbsent) {
        this.totalClassAbsent = totalClassAbsent;
    }
}
