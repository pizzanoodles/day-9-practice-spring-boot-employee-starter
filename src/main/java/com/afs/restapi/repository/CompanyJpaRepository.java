package com.afs.restapi.repository;

import com.afs.restapi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {
}
