package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
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

    @Test
    void should_return_specific_employee_when_find_by_id_given_employee_id() {
        //given
        Employee employee = new Employee(1L, "Joe Mama", 40, "Male", 5000);
        when(employeeJpaRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        //when
        Employee employeeResponse = employeeService.findById(employee.getId());
        //then
        assertEquals(employee, employeeResponse);
    }

    @Test
    void should_return_updated_employee_when_update_given_employee_id_and_updated_info() {
        //given
        Employee oldEmployee = new Employee(1L, "Jack", 23, "Male", 50000);
        Employee newEmployee = new Employee(1L, "Jack", 24, "Male", 55000);
        when(employeeJpaRepository.findById(oldEmployee.getId())).thenReturn(Optional.of(oldEmployee));
        //when
        employeeService.update(oldEmployee.getId(), newEmployee);
        //then
        verify(employeeJpaRepository, times(1)).save(oldEmployee);
    }

    @Test
    void should_return_female_employees_when_find_all_by_gender_given_some_employees() {
        //given
        Employee employee1 = new Employee(1L, "Robert", 19, "Male", 40000);
        Employee employee2 = new Employee(2L, "Madeline", 24, "Female", 50000);
        Employee employee3 = new Employee(3L, "Elizabeth", 22, "Female", 120000);
        List<Employee> allEmployees = List.of(employee1, employee2, employee3);
        List<Employee> femaleEmployees = List.of(employee2, employee3);
        when(employeeJpaRepository.findAllByGender("Female")).thenReturn(femaleEmployees);
        //when
        List<Employee> femaleEmployeesResponse = employeeService.findAllByGender("Female");
        //then
        assertNotEquals(allEmployees.size(), femaleEmployeesResponse.size());
        assertEquals(femaleEmployees, femaleEmployeesResponse);
    }

    @Test
    void should_return_created_employee_when_create_given_new_employee() {
        //given
        Employee toBeCreatedEmployee = new Employee(null, "Jens", 23, "Male", 100000);
        Employee savedEmployee = new Employee(1L, "Jens", 23, "Male", 100000);
        when(employeeJpaRepository.save(toBeCreatedEmployee)).thenReturn(savedEmployee);
        //when
        Employee employeeResponse = employeeService.create(toBeCreatedEmployee);
        //then
        assertEquals(savedEmployee, employeeResponse);
    }

    @Test
    void should_return_list_of_employee_by_page_when_find_by_page_given_some_employees() {
        //given
        Employee employee1 = new Employee(1L, "Robert", 19, "Male", 40000);
        Employee employee2 = new Employee(2L, "Madeline", 24, "Female", 50000);
        Employee employee3 = new Employee(3L, "Elizabeth", 22, "Female", 120000);
        Page<Employee> firstTwoEmployees = new PageImpl<>(List.of(employee1, employee2));
        List<Employee> allEmployees = List.of(employee1, employee2, employee3);
        Integer pageNumber = 1;
        Integer pageSize = 2;
        when(employeeJpaRepository.findAll(PageRequest.of(pageNumber - 1, pageSize))).thenReturn(firstTwoEmployees);
        //when
        List<Employee> employeesByPageResponse = employeeService.findByPage(pageNumber, pageSize);
        //then
        assertEquals(firstTwoEmployees.toList(), employeesByPageResponse);
        assertNotEquals(allEmployees, employeesByPageResponse);
    }

    @Test
    void should_delete_employee_when_delete_given_employee_id_of_employee_to_be_deleted() {
        //given
        Employee employee = new Employee(1L, "Jens", 23, "Male", 100000);
        //when
        employeeService.delete(employee.getId());
        //then
        verify(employeeJpaRepository, times(1)).deleteById(employee.getId());
    }
}
