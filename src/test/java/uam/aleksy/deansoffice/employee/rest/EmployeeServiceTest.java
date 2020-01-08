package uam.aleksy.deansoffice.employee.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetEmployee_2OO_OK() throws Exception {

        // given
        String employeeName = "test";
        Long id = 10L;
        Employee employee = new Employee();
        employee.setName(employeeName);
        employee.setId(id);
        employeeRepository.addEmployees(Collections.singletonList(employee));

        // when then
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value(employeeName))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.busy").exists())
                .andExpect(jsonPath("$.energyLeft").exists())
                .andExpect(jsonPath("$.initialEnergy").exists())
                .andExpect(jsonPath("$.name").value(employeeName))
                .andExpect(jsonPath("$.currentActivityIndex").exists());
    }

    @Test
    public void testGetEmployee_404_NOT_FOUND() throws Exception {

        // when then
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/3333")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}

