package com.attendance.domain.repository;

import com.attendance.domain.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by developer on 19/1/2561.
 */
@Repository
public interface HolidayRepository extends JpaRepository<Holiday,Integer>{

    Holiday findOneByDate(Date date);
}
