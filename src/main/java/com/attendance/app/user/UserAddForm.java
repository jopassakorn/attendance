package com.attendance.app.user;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by developer on 22/12/2560.
 */
@Data
public class UserAddForm {

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message="Invalid username!")
    @NotNull
    @Size(min=6, max=20)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message="Invalid password!")
    @NotNull
    @Size(min=6,max=20)
    private String password;
    //.+@.+\.[a-z]+
//    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Invalid email address!")
    @Email
    @NotNull
    @Size(max=40, message = "email must contain only 40 characters")
    private String email;

    @NotNull
    private String repeatPassword;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message="Invalid first name!")
    @NotNull
    @Size(min=2,max=20)
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message="Invalid last name!")
    @NotNull
    @Size(min=2,max=20)
    private String lastName;

    private String role;

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getRepeatPassword(){
        return repeatPassword;
    }

    public void setRepeatPassword(String password){
        this.repeatPassword = password;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){ this.firstName = firstName;}

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){ this.lastName = lastName;}

    public String getEmail(){ return email;}

    public void setEmail(String email) { this.email = email;}
}
