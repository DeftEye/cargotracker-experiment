package net.java.cargotracker.modern.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TrackControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void trimsIdAndShowsStatusEtaAndHistory() throws Exception {
        mvc.perform(post("/public/track.xhtml").param(TrackController.TRACKING_ID_FIELD, "  ABC123  "))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("is currently <strong>In port New York</strong>")))
                .andExpect(content().string(containsString("03/12/2016 12:00 AM UTC")))
                .andExpect(content().string(containsString("Unloaded off voyage 0100S in New York")));
    }

    @Test
    void misdirectedCargoShowsFlagAndNoEta() throws Exception {
        mvc.perform(post("/public/track.xhtml").param(TrackController.TRACKING_ID_FIELD, "JKL567"))
                .andExpect(content().string(containsString("Cargo is misdirected.")))
                .andExpect(content().string(containsString("fa fa-flag")))
                .andExpect(content().string(containsString("<span>?</span>")));
    }

    @Test
    void unknownIdRendersNoResultAndNoMessage() throws Exception {
        mvc.perform(post("/public/track.xhtml").param(TrackController.TRACKING_ID_FIELD, "NOPE99"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("id=\"result\""))))
                .andExpect(content().string(not(containsString("not found"))));
    }
}
