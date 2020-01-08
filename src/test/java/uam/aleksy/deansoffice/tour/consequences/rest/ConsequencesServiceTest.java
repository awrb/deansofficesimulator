package uam.aleksy.deansoffice.tour.consequences.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uam.aleksy.deansoffice.applicant.ApplicantRepository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.tour.consequences.ConsequenceRepository;
import uam.aleksy.deansoffice.tour.consequences.data.StudentConsequences;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ConsequencesServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConsequenceRepository consequenceRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Test
    public void testGetConsequence_200_OK() throws Exception {
        // given
        Long id = 200L;
        Applicant applicant = new Student();
        applicant.setName("test");
        Student studentWithIdSet = (Student) applicantRepository.add(applicant);

        int beersToDrink = 5;
        float markPunishment = 1f;

        StudentConsequences studentConsequences = new StudentConsequences(studentWithIdSet);
        studentConsequences.setBeersToDrink(beersToDrink);
        studentConsequences.setMarkPunishment(markPunishment);

        consequenceRepository.addConsequenceForApplicant(applicant, studentConsequences);

        // when then
        mockMvc.perform(MockMvcRequestBuilders.get("/consequences/" + studentWithIdSet.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.applicant.name").value("test"))
                .andExpect(jsonPath("$.beersToDrink").value(beersToDrink))
                .andExpect(jsonPath("$.markPunishment").value(markPunishment));
    }

    @Test
    public void testGetConsequence_404_NOT_FOUND() throws Exception {
        // given
        Long id = 200L;
        Applicant applicant = new Student();
        applicant.setName("test");
        Student studentWithIdSet = (Student) applicantRepository.add(applicant);

        // when then
        mockMvc.perform(MockMvcRequestBuilders.get("/consequences/" + studentWithIdSet.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
