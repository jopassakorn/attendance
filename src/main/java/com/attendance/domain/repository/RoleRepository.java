package com.attendance.domain.repository;

import com.attendance.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developer on 3/1/2561.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{

    @Query(value = "SELECT role_name FROM `user_role` where user_id = :userId",nativeQuery = true)
    List<String> findAllByUserId(@Param("userId") int userId);

    Role findByRoleName(String roleName);
}
