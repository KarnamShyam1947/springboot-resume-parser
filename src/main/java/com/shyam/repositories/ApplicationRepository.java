package com.shyam.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shyam.dto.MyApplications;
import com.shyam.dto.UserApplicationDTO;
import com.shyam.entities.ApplicationEntity;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {
    @Query(value = 
    """
        SELECT
            new com.shyam.entities.ApplicationEntity(a.id, a.jobId, a.userId, a.uuid, a.score, a.resumeUrl)
        FROM
            ApplicationEntity a
        WHERE
            a.jobId = :jobId
        AND
            a.userId = :userId""")
    public List<ApplicationEntity> isUserApplied(@Param("jobId") int jobId, @Param("userId") int usedId);

    @Query(value = """
        SELECT
            new com.shyam.dto.UserApplicationDTO(a.id, a.score, a.resumeUrl, u.name, u.email)
        FROM
            ApplicationEntity a
        JOIN
            UserEntity u
        ON
            a.userId = u.id
        AND
            a.jobId = :jobId""")
    public List<UserApplicationDTO> getApplications(@Param("jobId") int jobId);

    @Query(value = """
        SELECT
            new com.shyam.dto.MyApplications(a.id, a.score, a.resumeUrl, j.id, j.jobTitle, j.experince, j.salary)
        FROM
            ApplicationEntity a
        JOIN
            JobEntity j
        ON
            a.jobId = j.id
        AND
            a.userId = :userId
            """)
    public List<MyApplications> getUserApplications(@Param("userId") int userId);
}
