package com.shyam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.entities.CompanyEntity;


public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
    CompanyEntity findById(int id);
}
