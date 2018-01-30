package com.attendance.domain.entity;

import javax.persistence.*;

/**
 * Created by developer on 3/1/2561.
 */

@Entity
@Table(name = "user_role")
public class Role {

    private int userId;
    private String roleName;

    @Id
    @Column(name = "user_id")
    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    @Basic
    @Column(name = "role_name")
    public String getRoleName(){
        return roleName;
    }

    public void setRoleName(String roleName){
        this.roleName = roleName;
    }
}
