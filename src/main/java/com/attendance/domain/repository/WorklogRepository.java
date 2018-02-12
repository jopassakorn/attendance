package com.attendance.domain.repository;

import com.attendance.domain.entity.Section;
import com.attendance.domain.entity.Worklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by developer on 19/1/2561.
 */
@Repository
public interface WorklogRepository extends JpaRepository<Worklog,Integer>{

    List<Worklog> findAllByUserId(int userId);

    @Query(value = "SELECT * FROM work_log where user_id = :userId and work_date BETWEEN :startdate and :enddate",nativeQuery = true)
    List<Worklog> findAllByUserIdAndStartDateAndEnddate(@Param("userId") int userId, @Param("startdate") Date startdate, @Param("enddate") Date enddate);
}
