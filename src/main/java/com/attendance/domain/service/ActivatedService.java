package com.attendance.domain.service;

import com.attendance.domain.entity.Activated;
import com.attendance.domain.entity.User;
import com.attendance.domain.repository.ActivatedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 5/2/2561.
 */
@Service
public class ActivatedService extends AppService{

    @Autowired
    ActivatedRepository activatedRepository;

    public List<Activated> getFalseActivatedList(User user){
        List<Activated> activateds = new ArrayList<>();
        for(Activated activated : activatedRepository.findAllByUserIdAndActivatedStatus(user.getId(),false)){
            if(!activated.isActivatedStatus()){
                activateds.add(activated);
            }
        }
        return activateds;
    }

    public void save(Activated activated){
        activatedRepository.save(activated);
    }
}
