package com.attendance.domain.repository;

import com.attendance.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by developer on 3/1/2561.
 */

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByEmail(String email);
    User findById(int id);
    User findByFinger(String finger);
    User findByActivatedCode(int activatedCode);
}
