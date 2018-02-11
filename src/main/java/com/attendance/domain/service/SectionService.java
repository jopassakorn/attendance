package com.attendance.domain.service;

import com.attendance.domain.entity.Section;
import com.attendance.domain.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by developer on 18/1/2561.
 */
@Service
public class SectionService extends AppService{

    @Autowired
    SectionRepository sectionRepository;

    public Section findNextSection(int userId){
        Date today = new Date();
        Section section;
        int day = today.getDay() - 1;
        if(day == -1){
            day = 6;
        }
        int count;
        int hour;
        if(today.getHours() < 12){
            section = sectionRepository.findAfternoonSectionByDayAndYearAndSemester(getCurrentSemester(),getCurrentYear(),day);
        }else{
            section = sectionRepository.findMorningSectionByDayAndYearAndSemester(getCurrentSemester(),getCurrentYear(),day + 1);
        }
        if(section == null) {
            if(day == 6){
                count = 0;
            }else{
                count = day + 1;
            }
            while (count != day){
                section = sectionRepository.findMorningSectionByDayAndYearAndSemester(getCurrentSemester(),getCurrentYear(),day);
                if(section != null){
                    return section;
                }else{
                    section = sectionRepository.findAfternoonSectionByDayAndYearAndSemester(getCurrentSemester(),getCurrentYear(),day);
                    if(section!= null){
                        return section;
                    }
                }
                if(count != 6){
                    count = count + 1;
                }else{
                    count = 0;
                }
            }
        }
        return section;
    }

    public Section findOne(int id){ return sectionRepository.findOne(id);}

    public void delete(int id){ sectionRepository.delete(id);}

    public void save(Section section){ sectionRepository.save(section);}

    public Page<Section> getSectionPages(Pageable pageable){return sectionRepository.findAll(pageable);}

    public Page<Section> getUserCurrentSectionPages(int id,int semester, int year, Pageable pageable){ return sectionRepository.findAllSectionByUserIdAndSemesterAndYear(id,semester,year,pageable);}

    public List<Section> getAllSectionByUserId(int userId){return sectionRepository.findAllSectionByUserId(userId);}

    public List<Section> findAllCurrentSectionByUserId(int userId){return sectionRepository.findAllSectionByUserIdAndSemesterAndYear(userId,getCurrentSemester(),getCurrentYear());}

    public Section findOneBySecStartedAndSemesterAndYearAndDayAndRoom(int secstarted,int semester,int year,int day,int room){
        if(secstarted < 36000){
            return sectionRepository.findMorningSectionByDayAndYearAndSemesterAndRoom(semester,year,day,room);
        }else{
            return sectionRepository.findAfternoonSectionByDayAndYearAndSemesterAndRoom(semester,year,day,room);
        }
    }
}
