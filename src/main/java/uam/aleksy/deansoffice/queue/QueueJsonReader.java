package uam.aleksy.deansoffice.queue;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.simulation.SimulationInitializationException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Log
public class QueueJsonReader {

    private static final String EMPLOYEES_RESOURCE_PATH = "data/employees.json";
    private static final String APPLICANTS_RESOURCE_PATH = "data/applicants.json";
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static List<Applicant> readApplicants() throws SimulationInitializationException {
        return read(APPLICANTS_RESOURCE_PATH, Applicant.class);
    }

    public static List<Employee> readEmployees() throws SimulationInitializationException {
        return read(EMPLOYEES_RESOURCE_PATH, Employee.class);
    }

    private static <T> List<T> read(String resourceName, Class T) throws SimulationInitializationException {

        URL resourceUrl = QueueJsonReader.class.getClassLoader().getResource(resourceName);

        if (resourceUrl == null) {
            throw new SimulationInitializationException("RESOURCE FAIL");
        }

        try {
            return mapper.readValue(resourceUrl, mapper.getTypeFactory().constructCollectionType(List.class, T));
        } catch (JsonParseException | JsonMappingException e) {
            throw new SimulationInitializationException("JSON FAIL", e);
        } catch (IOException e2) {
            throw new SimulationInitializationException("FILE FAIL", e2);
        }
    }
}
