package com.shyam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shyam.dto.JobDetailsDTO;
import com.shyam.entities.JobEntity;
import java.util.List;


@Repository
public interface JobRepository extends JpaRepository<JobEntity, Integer> {
    // JobEntity findById(int id);
    List<JobEntity> findByHrId(int hrId);
    
    @Query(value = """
        SELECT 
            new com.shyam.dto.JobDetailsDTO(j.avaliblePosts, j.experince, j.jobTitle, j.jobDiscription, u.companyAddress, u.companyName, u.companyWebsiteUrl, u.email, u.name)
        FROM 
            JobEntity j
        JOIN
            UserEntity u
        ON
            j.hrId = u.id
        WHERE
            j.id = :id
    """)
    public JobDetailsDTO getJobDetails(@Param("id") int id);
}
