-- SELECT
--     j.job_title, j.salary, j.job_discription, j.end_date, j.experience, j.avalible_posts, c.id, c.name, c.logo_url, c.address
-- FROM
--     jobs j
-- JOIN
--     companies c
-- ON
--     j.company_id = c.id
-- AND
--     j.id = 7;

-- SELECT
--     j.id, j.job_title, j.salary, j.job_type, j.experience, j.end_date, j.avalible_posts, c.id, c.name, c.address, c.logo_url
-- FROM
--     jobs j
-- JOIN 
--     companies c
-- ON
--     j.company_id = c.id

SELECT
    a.id, a.score, a.resume_url, j.id, j.job_title, j.salary, c.id, c.name, c.address, c.logo_url
FROM
    applications a
JOIN
    jobs j 
ON 
    a.job_id = j.id
JOIN
    companies c 
ON 
    j.company_id = c.id
WHERE
    a.user_id = 1;


