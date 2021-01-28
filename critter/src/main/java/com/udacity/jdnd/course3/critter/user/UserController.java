package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer newCustomer = new Customer();
        newCustomer.setName(customerDTO.getName());
        newCustomer.setNotes(customerDTO.getNotes());
        newCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        //newCustomer.setPetIds(customerDTO.getPetIds());
        Customer savedCustomer = customerService.save(newCustomer);
        customerDTO.setId(savedCustomer.getId());
        //customerService.save(newCustomer);
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return convertCustomerListToCustomerDTOList(customerService.findAllCustomers());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")//save Employee
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee = new Employee();
        BeanUtils.copyProperties(employeeDTO,newEmployee);
        Employee savedEmployee = employeeService.save(newEmployee);
        employeeDTO.setId(savedEmployee.getId());
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}") //get Employee
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee foundEmployee = employeeService.findById(employeeId);
        EmployeeDTO retEmployeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(foundEmployee,retEmployeeDTO);
//        retEmployeeDTO.setId(foundEmployee.getId());
//        retEmployeeDTO.setName(foundEmployee.getName());
//        retEmployeeDTO.setSkills(foundEmployee.getSkills());
//        retEmployeeDTO.setDaysAvailable(foundEmployee.getDaysAvailable());
        return retEmployeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee tmp = employeeService.findById(employeeId);
        tmp.setDaysAvailable(daysAvailable);
        employeeService.save(tmp);
        //employeeService.findById(employeeId).setDaysAvailable(daysAvailable);
        //throw new UnsupportedOperationException();//maybe hier saven
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        LocalDate date = employeeDTO.getDate();

        //System.out.println(date.getDayOfWeek());
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        List<Employee> availableEmployees = employeeService.findAvailableEmployeesFor(skills,date.getDayOfWeek());
        return convertEmployeeListToEmployeeDTOList(availableEmployees);
        //throw new UnsupportedOperationException();
    }

    private List<EmployeeDTO> convertEmployeeListToEmployeeDTOList(List<Employee> employeeList){
        List<EmployeeDTO> retEmployeeDTOList = new ArrayList<>();
        for(Employee e : employeeList){
            retEmployeeDTOList.add(convertEmployeeToDTO(e));
        }
        return retEmployeeDTOList;
    }

    private Employee convertDTOtoEmployee(EmployeeDTO employeeDTO){
        Employee resEmployee = new Employee();
        BeanUtils.copyProperties(employeeDTO,resEmployee);
        return resEmployee;
    }

    private EmployeeDTO convertEmployeeToDTO(Employee employee){
        EmployeeDTO resEmployeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,resEmployeeDTO);
        return  resEmployeeDTO;
    }


    private List<CustomerDTO> convertCustomerListToCustomerDTOList(List<Customer> customerList){
        List<CustomerDTO> retCustomerDTOList = new ArrayList<>();
        for(Customer c : customerList){
            retCustomerDTOList.add(convertCustomerToDTO(c));
        }
        return retCustomerDTOList;
    }

    private Customer convertDTOtoCustomer(CustomerDTO customerDTO){
        Customer resCustomer = new Customer();
        BeanUtils.copyProperties(customerDTO,resCustomer);
        return resCustomer;
    }

    private CustomerDTO convertCustomerToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }

}
