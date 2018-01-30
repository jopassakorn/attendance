package com.attendance.domain.repository;

import com.attendance.domain.entity.Sectionlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developer on 18/1/2561.
 */
@Repository
public interface SectionlogRepository extends JpaRepository<Sectionlog,Integer> {

    List<Sectionlog> findAllBySectionId(int sectionId);

}
