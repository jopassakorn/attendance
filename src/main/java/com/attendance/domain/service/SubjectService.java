package com.attendance.domain.service;

import com.attendance.domain.repository.SubjectRepository;
import com.attendance.domain.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by developer on 8/1/2561.
 */
@Service
public class SubjectService extends AppService{

    @Autowired
    SubjectRepository subjectRepository;

    public List<Subject> getAllByDeleteStatus(boolean status){ return subjectRepository.findAllByDeleteStatus(status);}

    public void save(Subject subject){ subjectRepository.save(subject);}

    public List<Subject> getAllSubject(){ return subjectRepository.findAll();}

    public Page<Subject> getAllPageSubject(Pageable pageable){ return subjectRepository.findAllByDeleteStatus(false,pageable);}

    public void delete(int id){
        Subject subject = subjectRepository.findOne(id);
        subject.setDeleteStatus(true);
        subjectRepository.save(subject);
    }

    public Subject findOneById(int id){ return subjectRepository.findOne(id);}

    public Subject findByName(String name){ return subjectRepository.findOneByName(name);}

    public Subject findByCode(String code){ return subjectRepository.findOneByCode(code);}

}
