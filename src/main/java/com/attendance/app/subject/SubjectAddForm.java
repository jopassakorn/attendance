package com.attendance.app.subject;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by developer on 8/1/2561.
 */

public class SubjectAddForm {

    @Pattern(regexp = "^[0-9]+$", message="Invalid username!")
    @Size(min=1, max=7)
    private
    String code;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message="Invalid subject name!")
    @NotNull
    @Size(min=2, max=50)
    private
    String name;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message="Invalid subject name!")
    @NotNull
    @Size(min=2, max=50)
    private
    String department;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message="Invalid subject name!")
    @NotNull
    @Size(min=2, max=50)
    private
    String faculty;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private
    Date created;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private
    Date edited;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }
}
