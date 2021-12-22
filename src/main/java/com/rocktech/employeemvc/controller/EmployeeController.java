package com.rocktech.employeemvc.controller;

import com.rocktech.employeemvc.model.Employee;
import com.rocktech.employeemvc.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String homepage(Model model){
        model.addAttribute("employees", employeeService.getAllEmployee());
        return findPaginated(1, "firstName", "asc", model);
    }

    @GetMapping("/add")
    public String newEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "create";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") long id, Model model){
        Employee employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);
        return "edit";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("employee") Employee employee) {

        Employee exEmployee = employeeService.getEmployeeById(id);
        exEmployee.setFirstName(employee.getFirstName());
        exEmployee.setLastName(employee.getLastName());
        exEmployee.setEmail(employee.getEmail());

        employeeService.updateEmployee(exEmployee);

        return "redirect:/";

    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id){
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        int pageSize = 5;

        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Employee> employees = page.getContent();
        model.addAttribute("currentPageNo", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("employees", employees);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return  "index";
    }
}
