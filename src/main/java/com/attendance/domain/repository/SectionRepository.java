package com.attendance.domain.repository;

import com.attendance.domain.entity.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developer on 18/1/2561.
 */
@Repository
public interface SectionRepository extends JpaRepository<Section,Integer>{

    Page<Section> findAllSectionByUserIdAndSemesterAndYear(int userId,int semester, int year, Pageable pageable);

    List<Section> findAllSectionByUserIdAndSemesterAndYear(int userId,int semester, int year);

    List<Section> findAllSectionByUserId(int userId);

    @Query(value = "SELECT * FROM `section` WHERE sec_started > 43200 and semester = :semester and year = :year and day = :day and room_id = :room",nativeQuery = true)
    Section findAfternoonSectionByDayAndYearAndSemesterAndRoom(@Param("semester")int semester, @Param("year")int year, @Param("day")int day, @Param("room")int room);

    @Query(value = "SELECT * FROM `section` WHERE sec_started < 43200 and semester = :semester and year = :year and day = :day and room_id = :room",nativeQuery = true)
    Section findMorningSectionByDayAndYearAndSemesterAndRoom(@Param("semester")int semester, @Param("year")int year, @Param("day")int day, @Param("room")int room);

    @Query(value = "SELECT * FROM `section` WHERE sec_started < 43200 and semester = :semester and year = :year and day = :day",nativeQuery = true)
    Section findMorningSectionByDayAndYearAndSemester(@Param("semester")int semester,@Param("year")int year,@Param("day")int day);

    @Query(value = "SELECT * FROM `section` WHERE sec_started > 43200 and semester = :semester and year = :year and day = :day",nativeQuery = true)
    Section findAfternoonSectionByDayAndYearAndSemester(@Param("semester")int semester,@Param("year")int year,@Param("day")int day);

    @Query(value = "SELECT * FROM `section` WHERE semester != :semester and year != :year and user_id = :id",nativeQuery = true)
    List<Section> findAllPastSection(@Param("id")int id,@Param("semester")int semester,@Param("year")int year);
}
