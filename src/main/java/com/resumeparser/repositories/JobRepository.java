package com.resumeparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.resumeparser.dto.JobCompanyDTO;
import com.resumeparser.dto.JobDetailsDTO;
import com.resumeparser.entities.JobEntity;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Integer> {
    List<JobEntity> findByCompanyId(int companyId);

    @Query(value = """
        SELECT
            new com.resumeparser.dto.JobDetailsDTO(j.id, j.jobTitle, j.salary, j.jobDiscription, j.jobType, j.endDate, j.experience, j.avaliblePosts, c.id, c.name, c.logoUrl, c.address)
        FROM
            JobEntity j
        JOIN
            CompanyEntity c
        ON
            j.companyId = c.id
        AND
            j.id = :jobId """)
    JobDetailsDTO getJobDetails(@Param("jobId") int jobId);

    @Query(value = """
        SELECT
            new com.resumeparser.dto.JobCompanyDTO(j.id, j.jobTitle, j.salary, j.jobType, j.experience, j.endDate, j.avaliblePosts, c.id, c.name, c.address, c.logoUrl)
        FROM
            JobEntity j
        JOIN 
            CompanyEntity c
        ON
            j.companyId = c.id
            """)
    List<JobCompanyDTO> getCompanyJobDetails(); 
}
