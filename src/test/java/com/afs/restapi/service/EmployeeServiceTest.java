package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    private EmployeeService employeeService;
    private EmployeeJpaRepository employeeJpaRepository;

    @BeforeEach
    void setUp() {
        employeeJpaRepository = mock(EmployeeJpaRepository.class);
        employeeService = new EmployeeService(employeeJpaRepository);
    }

    @Test
    void should_return_all_employees_when_find_all_given_some_employees() {
        //given
        Employee employee1 = new Employee(1L, "Robert", 19, "Male", 40000);
        Employee employee2 = new Employee(2L, "Madeline", 24, "Female", 50000);
        Employee employee3 = new Employee(3L, "Elizabeth", 22, "Female", 120000);
        List<Employee> employees = List.of(employee1, employee2, employee3);
        when(employeeJpaRepository.findAll()).thenReturn(employees);
        //when
        List<Employee> allEmployeesResponse = employeeService.findAll();
        //then
        assertEquals(employees, allEmployeesResponse);
    }
}
