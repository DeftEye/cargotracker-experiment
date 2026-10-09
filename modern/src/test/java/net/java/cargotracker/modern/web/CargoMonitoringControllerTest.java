package net.java.cargotracker.modern.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CargoMonitoringControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void listsSampleCargoWithLegacyKeyOrder() throws Exception {
        String firstObject = "{\"trackingId\":\"ABC123\",\"routingStatus\":\"ROUTED\",\"misdirected\":false,"
                + "\"transportStatus\":\"IN_PORT\",\"atDestination\":false,\"origin\":\"CNHKG\","
                + "\"lastKnownLocation\":\"USNYC\"}";
        mvc.perform(get("/rest/cargo"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(org.hamcrest.Matchers.startsWith("[" + firstObject)))
                .andExpect(jsonPath("$[2].lastKnownLocation").value("Unknown"))
                .andExpect(jsonPath("$[3].routingStatus").value("MISROUTED"));
    }
}
