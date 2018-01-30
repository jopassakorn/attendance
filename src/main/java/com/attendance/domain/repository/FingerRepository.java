package com.attendance.domain.repository;

import com.attendance.domain.entity.Fingerprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developer on 17/1/2561.
 */
@Repository
public interface FingerRepository extends JpaRepository<Fingerprint,Integer>{

    Fingerprint findOneByFingerprintId(String FingerprintId);
}
