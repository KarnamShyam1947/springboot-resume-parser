package com.resumeparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.resumeparser.dto.UserApplicationDTO;
import com.resumeparser.entities.ApplicationEntity;
import java.util.List;


@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {
    List<ApplicationEntity> findByJobId(int jobId);

    @Query(value = """
        SELECT
            new com.resumeparser.dto.UserApplicationDTO(a.id, a.score, a.resumeUrl, j.id, j.jobTitle, j.salary, c.id, c.name, c.address, c.logoUrl)
        FROM
            ApplicationEntity a
        JOIN
            JobEntity j 
        ON 
            a.jobId = j.id
        JOIN
            CompanyEntity c 
        ON 
            j.companyId = c.id
        WHERE
            a.userId = :userId
            """)
    public List<UserApplicationDTO> getUserApplications(@Param("userId") int userId);

    @Query(value = 
    """
        SELECT
            new com.resumeparser.entities.ApplicationEntity(a.id, a.jobId, a.userId, a.uuid, a.score, a.resumeUrl)
        FROM
            ApplicationEntity a
        WHERE
            a.jobId = :jobId
        AND
            a.userId = :userId""")
    public List<ApplicationEntity> isUserApplied(@Param("jobId") int jobId, @Param("userId") int usedId);
}
