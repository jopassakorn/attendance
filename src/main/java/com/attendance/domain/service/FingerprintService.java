package com.attendance.domain.service;

import com.attendance.domain.entity.Fingerprint;
import com.attendance.domain.repository.FingerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by developer on 17/1/2561.
 */
@Service
public class FingerprintService extends AppService{

    @Autowired
    FingerRepository fingerRepository;

    public void save(Fingerprint fingerprint){ fingerRepository.save(fingerprint);}

    public List<Fingerprint> getAll(){return fingerRepository.findAll();}

    public Fingerprint findOne(int id){return fingerRepository.findOne(id);}

    public Fingerprint findOneById(String id){ return fingerRepository.findOneByFingerprintId(id);}
}
