package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyJpaRepository;
import com.afs.restapi.repository.EmployeeJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class CompanyServiceTest {
    private CompanyService companyService;
    private CompanyJpaRepository companyJpaRepository;
    private EmployeeJpaRepository employeeJpaRepository;

    @BeforeEach
    void setUp() {
        companyJpaRepository = mock(CompanyJpaRepository.class);
        employeeJpaRepository = mock(EmployeeJpaRepository.class);
        companyService = new CompanyService(companyJpaRepository, employeeJpaRepository);
    }

    @Test
    void should_return_all_companies_when_find_all_given_some_companies() {
        //given
        Company company1 = new Company(1L, "Orient Overseas Container Line.");
        Company company2 = new Company(2L, "COSCO Shipping Lines");
        Company company3 = new Company(3L, "Thoughtworks");
        List<Company> companies = List.of(company1, company2, company3);
        when(companyJpaRepository.findAll()).thenReturn(companies);
        //when
        List<Company> companiesResponse = companyService.findAll();
        //then
        assertEquals(companies, companiesResponse);
    }
    @Test
    void should_return_companies_by_page_when_find_by_page_given_some_companies() {
        //given
        Company company1 = new Company(1L, "Orient Overseas Container Line.");
        Company company2 = new Company(2L, "COSCO Shipping Lines");
        Company company3 = new Company(3L, "Thoughtworks");
        List<Company> companies = List.of(company1, company2, company3);
        Page<Company> firstTwoCompanies = new PageImpl<>(List.of(company1, company2));
        Integer pageNumber = 1;
        Integer pageSize = 2;
        when(companyJpaRepository.findAll(PageRequest.of(pageNumber - 1, pageSize))).thenReturn(firstTwoCompanies);
        //when
        List<Company> employeesByPageResponse = companyService.findByPage(pageNumber, pageSize);
        //then
        assertEquals(firstTwoCompanies.toList(), employeesByPageResponse);
        assertNotEquals(companies, employeesByPageResponse);
    }
}