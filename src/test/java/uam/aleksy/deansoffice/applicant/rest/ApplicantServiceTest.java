package uam.aleksy.deansoffice.applicant.rest;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.utils.JsonUtils;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicantServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfficeQueue officeQueue;

    @Test
    public void shouldAddNewApplicant() throws Exception {
        // given
        officeQueue.flush();

        String studentName = "test";
        Student student = new Student();
        student.setName(studentName);

        String data = JsonUtils.serialize(student);

        // when then
        mockMvc.perform(MockMvcRequestBuilders.post("/applicant").content(data)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentName));

        Assertions.assertEquals(officeQueue.peek().getName(), studentName);


    }

}
