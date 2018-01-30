package com.attendance.domain.service;

import com.attendance.domain.entity.Role;
import com.attendance.domain.repository.RoleRepository;
import com.attendance.domain.entity.User;
import com.attendance.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by developer on 3/1/2561.
 */

@Service
public class UserService extends AppService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public Page<User> findAll(Pageable pageable){return userRepository.findAll(pageable);}

    public User findOne(int id){return userRepository.findOne(id);}

    public String getAdminEmail(){ return userRepository.findById(roleRepository.findByRoleName("admin").getUserId()).getEmail();}

    public User findByEmail(String email){ return userRepository.findByEmail(email);}

    public List<String> findAllRoleByUserId(int userId){return roleRepository.findAllByUserId(userId);}

    public void saveUser(User user){userRepository.save(user);}

    public void saveRole(Role role){roleRepository.save(role);}

    public User fingByFinger(String finger){ return userRepository.findByFinger(finger);}
}
