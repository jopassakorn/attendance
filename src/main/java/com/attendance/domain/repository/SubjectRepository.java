package com.attendance.domain.repository;

import com.attendance.domain.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developer on 8/1/2561.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer>{

    Subject findOneByName(String name);
    Subject findOneByCode(String code);
    Page<Subject> findAllByDeleteStatus(boolean deleteStatus,Pageable pageable);
    List<Subject> findAllByDeleteStatus(boolean deleteStatus);
}
