# SpringBoot Resume Parser

**Platform:** Recruiters can post job listings. Job Seekers can browse and apply.
**Unique Feature:** Once a candidate applies, the platform analyzes the resume against the job description and assigns a **match percentage**, helping indicate how well-suited the candidate is for the position.



## 1. Overview

This project is a Spring Boot-powered resume parsing platform that connects **Recruiters** and **Job Seekers**. Recruiters can post jobs, and job seekers can view and apply. The system’s standout feature: when a candidate applies, the platform computes a **match percentage** reflecting how closely the resume aligns with the job description.

---

## 2. Features

* **Recruiter Interface**: Secure authentication, job posting, and job management.
* **Job Seeker Dashboard**: Account setup, job browsing, resume upload, and application tracking.
* **Match Analysis**: Intelligent comparison between resume content and job description to derive a candidate–job match score.
* **AWS S3 Integration**: Secure resume uploads and storage.
* **OAuth Support**: Login via Google or GitHub, powered by Spring Security.
* **Error Handling**: Custom messaging and pages for various HTTP error codes (403, 404, 500).
* **Email Services**: Notifications for account actions like password resets and application updates.

---

## 3. Tech Stack

* **Java & Spring Boot** (core framework)
* **Spring Security** with OAuth integration (Google, GitHub)
* **AWS S3** for file uploads (via `AWSS3Config`)
* **Thymeleaf**, HTML, CSS, JS for front-end templates and pages
* **DTOs / Entities / Repositories / Services** for clean architecture
* **Custom Validation**: e.g., future-date checks
* **Email**: FreeMarker (`.ftl` templates) based mailing service

---

## 4. Getting Started

### Prerequisites

* Java JDK 11+
* Maven
* Docker (optional, for containerization)
* AWS S3 account (with credentials)
* OAuth credentials (Google, GitHub)

### Setup & Installation

1. **Clone the repo**

   ```bash
   git clone https://github.com/karnamshyam1947/springboot‑resume‑parser.git
   cd springboot‑resume‑parser
   ```

2. **Copy and configure environment variables**

   ```bash
   cp .env.example .env
   # Update AWS S3 credentials, OAuth keys, email config, etc.
   ```

3. **Build and run the application**

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Or build a Docker container**

   ```bash
   docker build -t resume-parser-app .
   docker run -p 8080:8080 resume-parser-app
   ```

5. Visit `http://localhost:8080` in your browser.

---

## 5. Usage

* **For Recruiters**:

  * Register/log in
  * Post new jobs
  * View and manage your job listings and applications

* **For Job Seekers**:

  * Sign up/log in
  * Browse posted jobs
  * Upload your resume and apply
  * See your match percentage displayed after applying

---

## 6. Match Percentage Explained

This project’s key innovation—a match percentage—is calculated by comparing resume content with job description data. Though your implementation may vary, a typical approach might involve:

* Extracting keywords and skills from both resume and job description
* Comparing them via methods like cosine similarity or weighted keyword matching
* Producing a percentage score showing how well the candidate aligns with the role

This helps both recruiters and job seekers:

* **Recruiters**: Focus on higher‑match candidates
* **Job Seekers**: Understand how well your resume aligns and where to improve

---

## 7. Configuration & Environment

* **Profiles**: `application-dev.properties`, `application-prod.properties`, `application.properties`
* **AWS**: Configured in `aws.properties`, handled via `AWSS3Config.java`
* **OAuth**: Managed in `SecurityConfig.java` and related OAuth files
* **Email templates**: `.ftl` files under `src/main/resources/templates/email/`
