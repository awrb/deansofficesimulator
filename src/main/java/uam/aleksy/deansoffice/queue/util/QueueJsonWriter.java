package uam.aleksy.deansoffice.queue.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.queue.QueueDataGenerator;
import uam.aleksy.deansoffice.simulation.SimulationInitializationException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Component
/*
 * Utility bean that uses {@link QueueDataGenerator} to generate random queue data and then writes it into JSON files.
 */
public class QueueJsonWriter {

    private static final String EMPLOYEES_RESOURCE_PATH = "data/employees.json";
    private static final String APPLICANTS_RESOURCE_PATH = "data/applicants.json";
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    private QueueDataGenerator queueDataGenerator;

    @Autowired
    public QueueJsonWriter(QueueDataGenerator queueDataGenerator) {
        this.queueDataGenerator = queueDataGenerator;
    }

    public void writeApplicants() throws SimulationInitializationException, URISyntaxException {
        write(APPLICANTS_RESOURCE_PATH, queueDataGenerator.generateApplicants(), Applicant.class);
    }

    public void writeEmployees() throws SimulationInitializationException, URISyntaxException {
        write(EMPLOYEES_RESOURCE_PATH, queueDataGenerator.generateEmployees(), Employee.class);
    }

    private void write(String resourceName, Object data, Class<?> clazz) throws SimulationInitializationException, URISyntaxException {

        URL resourceUrl = QueueJsonWriter.class.getClassLoader().getResource(resourceName);

        if (resourceUrl == null) {
            throw new SimulationInitializationException("RESOURCE FAIL");
        }

        try {
            File file = new File(resourceUrl.toURI());
            if (clazz.equals(Applicant.class)) {
                // if we're serializing applicants, use a custom writer for abstract Applicant class
                ObjectWriter writer = mapper.writerFor(mapper.getTypeFactory().constructCollectionType(List.class, Applicant.class));
                writer.writeValue(file, data);
            } else {
                // otherwise just use standard write API
                mapper.writeValue(file, data);
            }

        } catch (JsonMappingException | JsonGenerationException e) {
            throw new SimulationInitializationException("WRITE FAIL", e);
        } catch (IOException e) {
            throw new SimulationInitializationException("FILE FAIL", e);
        }
    }

}
