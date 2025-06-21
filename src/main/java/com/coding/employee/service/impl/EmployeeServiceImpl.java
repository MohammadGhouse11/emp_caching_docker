package com.coding.employee.service.impl;

import com.coding.employee.dto.EmployeeDTO;
import com.coding.employee.entity.Employee;
import com.coding.employee.exception.BadRequestException;
import com.coding.employee.exception.ResourceNotFoundException;
import com.coding.employee.mapper.EmployeeMapper;
import com.coding.employee.repository.EmployeeRepository;
import com.coding.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override

    @CachePut(value = "employee", key = "#result.id", unless = "#result.salary < 10000")
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        /*if(employeeDTO.getName() == null || employeeDTO.getName().trim().isEmpty()) {
            throw new BadRequestException("Employee name cannot be empty");
        }*/
        logger.info("creating employee: {}",employeeDTO);
        Employee employee = EmployeeMapper.mapToEntity(employeeDTO);
        Employee saved = employeeRepository.save(employee);
        logger.info("Employee saved with Name: {}",saved.getName());
        logger.info("Returned DTO: {}", saved.getSalary());
        logger.info("Is salary eligible for caching: {}", saved.getSalary() != null && saved.getSalary() >= 10000);
        return EmployeeMapper.mapToDTO(saved);

    }

    @Override
    @Cacheable(value = "employee", key = "#id")
    public EmployeeDTO getEmployeeById(Long id) {
        logger.info("Fetching employee with id: {}",id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id Not Found: " + id));
       logger.debug("Employee retrieved: {}",employee);
        return EmployeeMapper.mapToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        logger.info("Fetching all employees");
        List<Employee> all = employeeRepository.findAll();
        logger.debug("Total employees found: {}",all.size());
        return all.stream().map(EmployeeMapper::mapToDTO).toList();
    }

    @Override
    @CachePut(value = "employee",key = "#id")
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO updatedEmployeeDTO) {
        logger.info("updating employee with id: {}",id);
        Employee existingEmp = employeeRepository.findById(id)
                        .orElseThrow(()->{
                            logger.error("Cannot update.Employee not found with id: {}",id);
                            return new ResourceNotFoundException("Employee with id Not Found: "+id);
                        });
        existingEmp.setName(updatedEmployeeDTO.getName());
        existingEmp.setEmail(updatedEmployeeDTO.getEmail());
        existingEmp.setDepartment(updatedEmployeeDTO.getDepartment());
        Employee saved = employeeRepository.save(existingEmp);
        logger.info("Employee updated : {}",saved.getName());
        return EmployeeMapper.mapToDTO(saved);
    }

    @Override
    @CachePut(value = "employee", key = "#id")
    public EmployeeDTO partialUpdateEmployee(Long id, EmployeeDTO partialUpdateEmpDTO) {
        logger.info("Partially updating employee with ID: {}", id);
        Employee existingEmp = employeeRepository.findById(id)
                .orElseThrow(()-> {
                    logger.error("Cannot update. Employee not found with ID: {}", id);
                    return new RuntimeException("Employee with id Not Found: "+id);
                });
        if(partialUpdateEmpDTO.getName()!=null && partialUpdateEmpDTO.getName()!=""){
            existingEmp.setName(partialUpdateEmpDTO.getName());
        }
        if(partialUpdateEmpDTO.getDepartment()!=null && partialUpdateEmpDTO.getDepartment()!=""){
            existingEmp.setDepartment(partialUpdateEmpDTO.getDepartment());
        }
        if(partialUpdateEmpDTO.getEmail()!=null && partialUpdateEmpDTO.getEmail()!=""){
            existingEmp.setEmail(partialUpdateEmpDTO.getEmail());
        }
        Employee saved = employeeRepository.save(existingEmp);
        logger.info("Employee partially updated: {}", saved.getName());
        return EmployeeMapper.mapToDTO(saved);
    }

    @Override
    @CacheEvict(value = "employee", key = "#id")
    public void deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        Employee existingEmp = employeeRepository.findById(id)
                        .orElseThrow(()->{
                            logger.error("Cannot delete. Employee not found with ID: {}", id);
                            return   new ResourceNotFoundException("Employee with id Not Found: "+id);});
        employeeRepository.delete(existingEmp);
        logger.info("Employee deleted: {}",id);
    }
}
