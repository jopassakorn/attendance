package com.attendance.domain.service;

import com.attendance.domain.entity.Semesterlog;
import com.attendance.domain.repository.SemesterlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by developer on 22/1/2561.
 */
@Service
public class SemesterlogService extends AppService {

    @Autowired
    SemesterlogRepository semesterlogRepository;

    public void save(Semesterlog semesterlog){
        semesterlogRepository.save(semesterlog);
    }

    public Semesterlog findOneBySemesterAndYear(int semester,int year){
        return semesterlogRepository.findOneBySemesterAndYear(semester,year);
    }

    public int getNextSemester(){
        int currentSemester = getCurrentSemester();
        if(currentSemester == 2){
            return 1;
        }else{
            return 2;
        }
    }

    public int getNextYear(){
        int currentSemester = getCurrentSemester();
        int year = getCurrentYear();
        if(currentSemester == 1){
            return year;
        }else{
            return year+1;
        }
    }
}
