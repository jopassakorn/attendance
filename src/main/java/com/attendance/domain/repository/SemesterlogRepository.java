package com.attendance.domain.repository;

import com.attendance.domain.entity.Semesterlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by developer on 19/1/2561.
 */
@Repository
public interface SemesterlogRepository extends JpaRepository<Semesterlog,Integer>{

    Semesterlog findOneBySemesterAndYear(int semester,int year);
}
