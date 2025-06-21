package com.coding.employee.mapper;

import com.coding.employee.dto.EmployeeDTO;
import com.coding.employee.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDTO mapToDTO(Employee employee){
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId()); // Needed for caching key
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setDesignation(employee.getDesignation());
        dto.setSalary(employee.getSalary());
        return dto;
    }

    public static Employee mapToEntity(EmployeeDTO employeeDTO){
        Employee emp = new Employee();
        emp.setName(employeeDTO.getName());
        emp.setDepartment(employeeDTO.getDepartment());
        emp.setEmail(employeeDTO.getEmail());
        emp.setDesignation(employeeDTO.getDesignation());
        emp.setSalary(employeeDTO.getSalary());
        return emp;
    }

}
