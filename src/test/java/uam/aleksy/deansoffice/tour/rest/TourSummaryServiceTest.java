package uam.aleksy.deansoffice.tour.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uam.aleksy.deansoffice.tour.summary.TourSummaryRepository;
import uam.aleksy.deansoffice.tour.summary.data.TourSummary;

@SpringBootTest
@AutoConfigureMockMvc
public class TourSummaryServiceTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TourSummaryRepository tourSummaryRepository;

    @Test
    public void shouldReturnTourSummaries() throws Exception {

        // given
        TourSummary tourSummary1 = new TourSummary();
        tourSummary1.setTourId(1L);

        TourSummary tourSummary2 = new TourSummary();
        tourSummary2.setTourId(2L);

        tourSummaryRepository.add(tourSummary1);
        tourSummaryRepository.add(tourSummary2);

        // when then
        mockMvc.perform(MockMvcRequestBuilders.get("/tour-summary")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tourId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].tourId").value(2L));
    }
}
