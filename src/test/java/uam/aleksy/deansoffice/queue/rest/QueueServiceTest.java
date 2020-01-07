package uam.aleksy.deansoffice.queue.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uam.aleksy.deansoffice.queue.OfficeQueue;

@SpringBootTest
@AutoConfigureMockMvc
public class QueueServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfficeQueue officeQueue;


    @Test
    public void testGetQueue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/queue")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.hasSize(officeQueue.getQueue().size())));
    }
}
