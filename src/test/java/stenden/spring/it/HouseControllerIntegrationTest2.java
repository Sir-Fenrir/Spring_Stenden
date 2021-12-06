package stenden.spring.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import stenden.spring.configuration.WebConfig;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringJUnitWebConfig(classes = {WebConfig.class, TestConfig.class})
public class HouseControllerIntegrationTest2 {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity()) // Required to enable Spring Security during the testing
                .build();
    }

    @Test
    @WithMockUser(username = "user")
    public void testRetrieveData() throws Exception {
        mockMvc.perform(
                        get("/data/jpa/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(
                        content().string("{\"id\":1,\"nrOfFloors\":4,\"nrOfRooms\":12,\"street\":\"Ubbo Emmiussingel 112\",\"city\":\"Groningen\"}")
                );
    }

}
