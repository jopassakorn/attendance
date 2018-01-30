package com.attendance.domain.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by developer on 8/1/2561.
 */
@Entity
@Table(name = "subject")
public class Subject {

    private int id;
    private String code;
    private String name;
    private String department;
    private String faculty;
    private Date created;
    private Date edited;
    private boolean deleteStatus;

    private List<Section> sections;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL)
    public List<Section> getSections(){return sections;}

    public void setSections(List<Section> sections){this.sections = sections;}

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Column(name = "code")
    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    @Column(name = "name")
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Column(name = "department")
    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    @Column(name = "faculty")
    public String getFaculty(){
        return faculty;
    }

    public void setFaculty(String faculty){
        this.faculty = faculty;
    }

    @Column(name = "created")
    public Date getCreated(){
        return created;
    }

    public void setCreated(Date created){
        this.created = created;
    }

    @Column(name = "edited")
    public Date getEdited(){
        return edited;
    }

    public void setEdited(Date edited){
        this.edited = edited;
    }

    @Column(name = "delete_status")
    public boolean getDeleteStatus(){ return deleteStatus;}

    public void setDeleteStatus(boolean deleteStatus){ this.deleteStatus = deleteStatus;}
}
