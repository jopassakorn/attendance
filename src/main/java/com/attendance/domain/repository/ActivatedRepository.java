package com.attendance.domain.repository;

import com.attendance.domain.entity.Activated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developer on 5/2/2561.
 */
@Repository
public interface ActivatedRepository extends JpaRepository<Activated,Integer>{

    List<Activated> findAllByUserIdAndActivatedStatus(int userId,boolean activatedStatus);
}
