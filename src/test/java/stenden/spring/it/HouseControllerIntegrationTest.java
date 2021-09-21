package stenden.spring.it;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import stenden.spring.configuration.WebConfig;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
public class HouseControllerIntegrationTest {

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
    public void testException() throws Exception {
        mockMvc.perform(
                get("/hello_world/error")
                        .header("Authorization", "Bearer " + validJWT())
        ).andDo(print()); // This is not a valid test, just an example of doing a request in an Integration Test
    }

    @Test
    public void testValidation() throws Exception {
        mockMvc.perform(
                post("/data")
                        .header("Authorization", "Bearer " + validJWT())
                        .content("{\n" +
                                "  \"nrOfFloors\": 2,\n" +
                                "  \"nrOfRooms\": 2,\n" +
                                "  \"street\": \"yesbutno\",\n" +
                                "  \"city\": \"no\"\n" +
                                "}\n")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()); // This is not a valid test, just an example of doing a request in an Integration Test
    }

    @Test
    public void testAuthentication() throws Exception {
        authenticate("user", "password")
                .andExpect(header().exists("jwt-token"));
    }

    @Test
    public void testRetrieveData() throws Exception {
        String jwt = validJWT();
        mockMvc.perform(
                        get("/data/jpa/1")
                                .header("Authorization", "Bearer " + jwt))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(
                        content().string("{\"id\":1,\"nrOfFloors\":4,\"nrOfRooms\":12,\"street\":\"Ubbo Emmiussingel 112\",\"city\":\"Groningen\"}")
                );
    }

    private ResultActions authenticate(String user, String password) throws Exception {
        return mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("username", user),
                                new BasicNameValuePair("password", password)
                        ))))
        );
    }

    private String validJWT() throws Exception {
        return authenticate("user", "password").andReturn().getResponse().getHeader("jwt-token");
    }

}
