package com.attendance.domain.service;

import com.attendance.app.user.UserResultShowForm;
import com.attendance.domain.entity.*;
import com.attendance.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 3/1/2561.
 */

@Service
public class UserService extends AppService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorklogRepository worklogRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    SectionlogRepository sectionlogRepository;

    public List<User> findAll(){return userRepository.findAll();}

    public Page<User> findAll(Pageable pageable){return userRepository.findAll(pageable);}

    public User findOne(int id){return userRepository.findOne(id);}

    public User findByEmail(String email){ return userRepository.findByEmail(email);}

    public void saveUser(User user){userRepository.save(user);}


    public User findByAcCode(int activatedCode){ return userRepository.findByActivatedCode(activatedCode);}

    public List<UserResultShowForm> getAllUserResultFormList(){
        List<User> userList = userRepository.findAll();
        List<UserResultShowForm> userResultShowForms = new ArrayList<>();
        for(User user : userList){
            if(!user.getEmail().equals("575021001004@mail.rmutk.ac.th") && !user.getEmail().equals("srisuda.s@mail.rmutk.ac.th")){
                UserResultShowForm userResultShowForm = new UserResultShowForm();
                userResultShowForm.setFirstName(user.getFirstName());
                userResultShowForm.setLastName(user.getLastName());
                List<Worklog> worklogList = worklogRepository.findAllByUserId(user.getId());
                int late = 0;
                int ontime = 0;
                int absent = 0;
                for(Worklog worklog : worklogList){
                    if(worklog.getStatus().equals("late")){
                        late = late + 1;
                    }else if(worklog.getStatus().equals("absent")){
                        absent = absent + 1;
                    }else if(worklog.getStatus().equals("ontime")){
                        ontime = ontime + 1;
                    }
                }
                userResultShowForm.setTotalWorkAbsent(absent);
                userResultShowForm.setTotalWorkLate(late);
                userResultShowForm.setTotalWorkOntime(ontime);

                ontime = 0;
                late = 0;
                absent = 0;

                List<Section> sectionList = sectionRepository.findAllSectionByUserId(user.getId());
                for(Section section : sectionList){
                    List<Sectionlog> sectionlogList = sectionlogRepository.findAllBySectionId(section.getId());
                    for (Sectionlog sectionlog : sectionlogList){
                        if(sectionlog.getStatus().equals("late")){
                            late = late + 1;
                        }else if(sectionlog.getStatus().equals("ontime")){
                            ontime = ontime + 1;
                        }else if(sectionlog.getStatus().equals("absent")){
                            absent = absent + 1;
                        }
                    }
                }

                userResultShowForm.setTotalClassAbsent(absent);
                userResultShowForm.setTotalClassLate(late);
                userResultShowForm.setTotalClassOntime(ontime);
                userResultShowForms.add(userResultShowForm);
            }
        }
        if(userResultShowForms.size() == 0){
            return null;
        }else{
            return userResultShowForms;
        }
    }
}
