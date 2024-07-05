package com.resumeparser.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.resumeparser.dto.JobApplicationDTO;
import com.resumeparser.entities.CompanyEntity;


public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
    CompanyEntity findById(int id);

    @Query(value = """
        SELECT
            new com.resumeparser.dto.JobApplicationDTO(u.id, u.name, u.profileUrl, a.id, a.score, a.resumeUrl)
        FROM
            ApplicationEntity a
        JOIN
            UserEntity u
        ON
            a.userId = u.id
        AND
            a.jobId = :jobId """)
    public List<JobApplicationDTO> getApplications(@Param("jobId") int jobId);

}
